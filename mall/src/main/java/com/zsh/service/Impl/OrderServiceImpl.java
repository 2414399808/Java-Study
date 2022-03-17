package com.zsh.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsh.domain.*;
import com.zsh.enums.OrderStatusEnum;
import com.zsh.enums.PaymentTypeEnum;
import com.zsh.enums.ProductStatusEnum;
import com.zsh.enums.ResponseEnum;
import com.zsh.mapper.OrderItemMapper;
import com.zsh.mapper.OrderMapper;
import com.zsh.mapper.ProductMapper;
import com.zsh.mapper.ShippingMapper;
import com.zsh.service.IOrderService;
import com.zsh.vo.OrderItemVo;
import com.zsh.vo.OrderVo;
import com.zsh.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CartServiceImpl cartService;


    @Override
    @Transactional
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {

        //收获地址校验
        Shipping shipping = shippingMapper.selectByUidAndShippingId(uid, shippingId);
        if (shipping == null) {
           return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }
        //获取购物车，校验 （是否有商品，库存）
        List<Cart> cartList = cartService.listCart(uid);
        List<Cart> cartSelected = new ArrayList<>();
        for (Cart cart : cartList) {
            if (cart.getProductSelected()) {
                cartSelected.add(cart);
            }
        }
        if (cartSelected.size() == 0) {
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }
        Set<String> collect = new HashSet<>();
        for (Cart cart : cartSelected) {
            collect.add(cart.getProductId().toString());
        }
        List<Product> products = productMapper.selectByKeySet(collect);
        Map<Integer, Product> map = products.stream().collect(Collectors.toMap(Product::getId, product -> product));
        List<OrderItem> orderItemList = new ArrayList<>();
        Long orderNO = generateOrderNo();
        for (Cart cart : cartSelected) {

            Product product = map.get(cart.getProductId());
            //是否有该商品
            if (product == null) {
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST, "商品不存在" + cart.getProductId());
            }

            //商品上下架状态
            if (!ProductStatusEnum.ON_SALE.getCode().equals(product.getStatus())) {
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE, product.getName() + "商品不是在售状态");
            }


            //库存是否充足
            if (cart.getQuantity() > product.getStock()) {
                return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR, product.getName() + "库存不正确");
            }


            OrderItem item = buildOrderItem(uid, orderNO, product, cart.getQuantity());
            orderItemList.add(item);
            //减库存
            product.setStock(product.getStock() - cart.getQuantity());
            int row = productMapper.updateByPrimaryKeySelective(product);
            if (row <= 0) {
                return ResponseVo.error(ResponseEnum.ERROR);
            }
        }
        //计算总价 只计算选中的商品
        //生成订单 入库 order 和orderItem 表
        Order order = buildOrder(uid, orderNO, shippingId, orderItemList);
        int rowForOrder = orderMapper.insertSelective(order);

        if (rowForOrder <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        int rowForOrderItem = orderItemMapper.batchInsertSelective(orderItemList);

        if (rowForOrderItem <= 0) {

            return ResponseVo.error(ResponseEnum.ERROR);
        }


        //更新购物车(选中的商品)
        //Redis有事务（打包命令） redis的命令是单线程的 不能回滚
        for (Cart cart : cartSelected) {
            cartService.delete(uid, cart.getProductId());

        }

        //构造orderVo对象
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);

        return ResponseVo.success(orderVo);
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.selectByUid(uid);

        Set<Long> orderNoSet = orderList.stream().map(Order::getOrderNo).collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);
        Map<Long,List<OrderItem>> orderItemMap =orderItemList.stream().collect(Collectors.groupingBy(OrderItem::getOrderNo));

        Set<Integer> shippingIdSet = orderList.stream().map(Order::getShippingId).collect(Collectors.toSet());
        List<Shipping> shippingList = shippingMapper.selectByidSet(shippingIdSet);
        Map<Integer,Shipping> shippingMap=shippingList.stream().collect(Collectors.toMap(Shipping::getId,shipping -> shipping));

        List<OrderVo> orderVoList =new ArrayList<>();
        for (Order order : orderList) {
            OrderVo orderVo = buildOrderVo(order, orderItemMap.get(order.getOrderNo()), shippingMap.get(order.getShippingId()));
            orderVoList.add(orderVo);
        }

        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);



        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null||!order.getUserId().equals(uid)){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        Set<Long> orderNoSet =new HashSet<>();
        orderNoSet.add(order.getOrderNo());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);

        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());

        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.success(orderVo);
    }

    @Override
    public ResponseVo cancel(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null||!order.getUserId().equals(uid)){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        //只有未付款订单可以取消
        if(!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())){
            return ResponseVo.error(ResponseEnum.ORDER_STATUS_ERROR);
        }

        order.setStatus(OrderStatusEnum.CANCELED.getCode());
        order.setCloseTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if(row<=0){
            return  ResponseVo.error(ResponseEnum.ERROR);
        }

        return  ResponseVo.success();
    }

    @Override
    public void paid(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null){
            throw  new RuntimeException(ResponseEnum.ORDER_NOT_EXIST.getDesc()+"订单id:"+orderNo);}
        //只有未付款订单可以已付款
        if(!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())){
           throw new RuntimeException(ResponseEnum.ORDER_STATUS_ERROR.getDesc()+"订单id:"+orderNo);
        }

        order.setStatus(OrderStatusEnum.PAID.getCode());
        order.setPaymentTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if(row<=0){
            throw new RuntimeException("将订单更新为已支付状态失败,订单id："+orderNo);
        }

    }

    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItemList, Shipping shipping) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        List<OrderItemVo> orderItemVoList = orderItemList.stream().map(e -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(e, orderItemVo);
            return orderItemVo;

        }).collect(Collectors.toList());
        if(shipping!=null){
        orderVo.setShippingId(shipping.getId());
        orderVo.setShippingVo(shipping);

        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;

    }

    private Order buildOrder(Integer uid, Long orderNo, Integer shippingId, List<OrderItem> orderItemList) {

        Order order = new Order();

        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        BigDecimal payment = orderItemList.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setPayment(payment);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());

        return order;


    }

    private Long generateOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt(999);
    }

    private OrderItem buildOrderItem(Integer uid, Long orderNo, Product product, Integer quantity) {
        OrderItem item = new OrderItem();
        item.setUserId(uid);
        item.setOrderNo(orderNo);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductImage(product.getMainImage());
        item.setCurrentUnitPrice(product.getPrice());
        item.setQuantity(quantity);
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return item;
    }
}
