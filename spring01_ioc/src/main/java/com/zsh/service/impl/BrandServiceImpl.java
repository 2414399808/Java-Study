package com.zsh.service.impl;

import com.zsh.dao.BrandDao;
import com.zsh.domain.Brand;
import com.zsh.service.BrandService;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class BrandServiceImpl implements BrandService {


    private BrandDao brandDao;



    public BrandServiceImpl() {
    }

    public void setBrandDao(BrandDao brandDao) {
        this.brandDao = brandDao;
    }

    @Override
    public void save() {
        brandDao.save();
    }
}
