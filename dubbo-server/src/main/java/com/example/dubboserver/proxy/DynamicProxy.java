package com.example.dubboserver.proxy;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
public class DynamicProxy implements InvocationHandler {
    private Object target;

    public  Object createProxy(Object target){
        //根据传递的参数 进行对象的关联
        this.target=target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        /**
         * before
         */
        log.info(String.format("proxy name is %s,method name is %s",proxy.getClass().getName(),method.getName()));
        Object result= method.invoke(this.target,args);
        /**
         * after
         */
        log.info("返回结果：{}",JSON.toJSON(result));
        return result;
    }
}
