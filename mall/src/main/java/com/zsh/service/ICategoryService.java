package com.zsh.service;


import com.zsh.vo.CategoryVo;
import com.zsh.vo.ResponseVo;

import java.util.List;
import java.util.Set;

public interface ICategoryService {
   ResponseVo<List<CategoryVo>> selectAll();

   void findSubCategoryId(Integer id, Set<Integer> resultSet);


}
