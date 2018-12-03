package com.example.dubboserver;

import com.example.dubboserver.common.MessageSendBuilder;
import com.example.dubboserver.common.MessageSender;
import com.example.dubboserver.domain.User;
import com.example.dubboserver.mapper.IUserMapper;
import com.example.dubboserver.proxy.DynamicProxy;
import com.example.dubboserver.service.IProductService;
import com.example.dubboserver.service.IUserService;
import com.example.dubboserver.service.ProductServiceImpl;
import com.example.dubboserver.service.UserServiceImpl;
import com.example.dubboserver.websocket.WebSocketSvr;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DubboServerApplicationTests {

    @Autowired
    MessageSendBuilder builder;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Resource(type = IUserMapper.class)
    IUserMapper userMapper;
    @Autowired
    UserServiceImpl service;
    @Autowired
    WebSocketSvr svr;


    @Test
    public void contextLoads() {
        MessageSender messageSender=builder.buildMessageSender("test_z1","zygx2","zygx2");
        messageSender.send("xixi");
    }

    @Test
    public void testRedis() {
//        Map m=new HashMap();
//        m.put("1",1);
//        m.put("2",2);
//        redisTemplate.opsForHash().putAll("map_z",m);
//        Map r=redisTemplate.opsForHash().entries("map_z");
//        r.forEach((o, o2) -> System.out.println("key="+o+",value="+o2));
//        System.out.println(redisTemplate.opsForHash().keys("map_z"));

//        User u=new User();
//        u.setId(3);
//        u.setUsername("zhangsan");
//        redisTemplate.opsForValue().set("user",u);
//        User u2=(User)redisTemplate.opsForValue().get("user");
//        System.out.println(u2.getUsername());

//        redisTemplate.opsForValue().setIfAbsent("zhang","安徽");
//        System.out.println(redisTemplate.opsForValue().get("zhang"));
//        redisTemplate.opsForList().leftPush("zygx_1","a");
//        redisTemplate.opsForList().leftPush("zygx_1","b");
//        redisTemplate.opsForList().leftPush("zygx_1","c");
//        redisTemplate.opsForList().leftPush("zygx_1","d");
//        List list= redisTemplate.opsForList().range("zygx_1",0,-1);
//        list.forEach(o -> System.out.println(o));
//
//        redisTemplate.opsForZSet().add("zygx_2","z",3);
//        redisTemplate.opsForZSet().add("zygx_2","y",1);
//        redisTemplate.opsForZSet().add("zygx_2","g",2);
//        redisTemplate.opsForZSet().add("zygx_2","x",8);
        Set<ZSetOperations.TypedTuple<Object>> set=redisTemplate.opsForZSet().rangeWithScores("zygx_2",0,8);
        set.forEach(objectTypedTuple -> System.out.println(objectTypedTuple.getValue()));
    }

    @Test
    public void testproxy() {
//        ProductServiceImpl service=new ProductServiceImpl();
//        IProductService productService=(IProductService)new DynamicProxy().createProxy(service);
//        System.out.println(productService.getProduct("apple"));
        IUserService userService=(IUserService)new DynamicProxy().createProxy(service);
        System.out.println(userService.getUser(2));
    }

}
