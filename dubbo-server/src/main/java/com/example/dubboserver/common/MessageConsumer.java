package com.example.dubboserver.common;

import com.example.dubboserver.response.BaseResponse;

import java.io.IOException;

public interface MessageConsumer {
    BaseResponse consume() throws IOException;
}
