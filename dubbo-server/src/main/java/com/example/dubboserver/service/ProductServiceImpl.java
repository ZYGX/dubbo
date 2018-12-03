package com.example.dubboserver.service;

import com.alibaba.dubbo.config.annotation.Service;

@Service(version = "2.0.0",interfaceClass = IProductService.class)
public class ProductServiceImpl implements IProductService {
    @Override
    public String getProduct(String name) {
        return name;
    }
    @Override
    public int getCount(int id) {
        return id;
    }
}
