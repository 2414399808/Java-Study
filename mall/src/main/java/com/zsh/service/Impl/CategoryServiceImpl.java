package com.zsh.service.Impl;

import com.zsh.consts.MallConst;
import com.zsh.domain.Category;
import com.zsh.mapper.CategoryMapper;
import com.zsh.service.ICategoryService;
import com.zsh.vo.CategoryVo;
import com.zsh.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        List<CategoryVo> categoryVoList=new ArrayList<>();
        List<Category> categories = categoryMapper.selectAll();

        //查出来parent_id=0的数据
        for (Category category : categories) {
            if(category.getParentId().equals(MallConst.ROOT_PARENT_ID)){
                CategoryVo categoryVo = category2CategoryVo(category);
                categoryVoList.add(categoryVo);
            }

        }
        categoryVoList.sort((o1, o2) -> o2.getSortOrder()-o1.getSortOrder());
        findSubCategory(categoryVoList,categories);

        return ResponseVo.success(categoryVoList);
    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categories = categoryMapper.selectAll();

                findSubCategoryId(id,resultSet,categories);


    }

    public void findSubCategoryId(Integer id, Set<Integer> resultSet,List<Category> categories) {

        for (Category category : categories) {
            if(category.getParentId().equals(id)){
                resultSet.add(category.getId());
                findSubCategoryId(category.getId(),resultSet,categories);
            }
        }
    }

    private void findSubCategory(List<CategoryVo> categoryVoList,List<Category> categoryList){
        for (CategoryVo categoryVo : categoryVoList) {
            List<CategoryVo> subCategoryVoList=new ArrayList<>();
            for (Category category : categoryList) {
                //如果查到内容，设置subCategory 继续往下查
                if(category.getParentId().equals(categoryVo.getId())){
                    CategoryVo subCategoryVo = category2CategoryVo(category);
                    subCategoryVoList.add(subCategoryVo);


                }
            }
            subCategoryVoList.sort((o1, o2) -> o2.getSortOrder()-o1.getSortOrder());
            categoryVo.setSubCategories(subCategoryVoList);
            findSubCategory(subCategoryVoList,categoryList);
        }
    }
    private CategoryVo category2CategoryVo(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}
