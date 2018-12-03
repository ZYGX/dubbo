package com.example.dubboserver.mapper;

import com.example.dubboserver.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMapper {
    int insert(User record);

    int insertSelective(User record);

    User findById(int id);

    String findNameById(int id);

}
