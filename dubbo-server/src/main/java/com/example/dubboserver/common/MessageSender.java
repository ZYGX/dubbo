package com.example.dubboserver.common;

import com.example.dubboserver.response.BaseResponse;

public interface MessageSender<T> {
    BaseResponse<T>send(Object message);
}
