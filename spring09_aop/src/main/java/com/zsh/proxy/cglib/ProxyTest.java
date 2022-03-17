package com.zsh.proxy.cglib;

import com.zsh.proxy.jdk.TargetInterface;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyTest {
    public static void main(String[] args) {

        //目标对象
        final Target target=new Target();
        //增强对象
        final Advice advice=new Advice();
//        //返回值 就是动态生成的代理对象 基于cglib
//        //1 、创建增强器
//        Enhancer enhancer=new Enhancer();
//        //2 、设置父类(目标)
//        enhancer.setSuperclass(Target.class);
//        //3 、设置回调
//        enhancer.setCallback(new MethodInterceptor() {
//            @Override
//            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
//                //执行前置
//                advice.before();
//                //执行目标
//                method.invoke(target,args);
//                //执行后置
//                advice.after();
//                return null;
//            }
//        });
//        //4 、创建代理对象
//        Target proxy = (Target) enhancer.create();
        Target proxy = ProxyUtils.getProxy(target);
        proxy.save();

    }
}
