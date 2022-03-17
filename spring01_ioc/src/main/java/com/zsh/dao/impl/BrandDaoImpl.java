package com.zsh.dao.impl;

import com.zsh.dao.BrandDao;
import com.zsh.domain.Brand;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class BrandDaoImpl implements BrandDao {
    private String name;
    private String age;
    private List<String> strList;
    private Map<String, Brand> brandMap;
    private Properties prop;
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setStrList(List<String> strList) {
        this.strList = strList;
    }

    public void setBrandMap(Map<String, Brand> brandMap) {
        this.brandMap = brandMap;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }
    @Override
    public void save() {
        System.out.println(name);
        System.out.println(age);
        System.out.println(strList);
        System.out.println(brandMap);
        System.out.println(prop);
        System.out.println("BrandDaoImpl save...");
    }
}
