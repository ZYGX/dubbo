package com.example.dubboserver.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dubboserver.mapper.IUserMapper;
import javax.annotation.Resource;

@Service(version = "1.0.0",interfaceClass = IUserService.class)
public class UserServiceImpl implements IUserService {
    @Resource(type = IUserMapper.class)
    IUserMapper userMapper;

    @Override
    public String getUser(int id) {
        System.out.println("param=>"+id);
        String response=userMapper.findNameById(id);
        System.out.println("response=>"+response);
        return response;
    }
}
