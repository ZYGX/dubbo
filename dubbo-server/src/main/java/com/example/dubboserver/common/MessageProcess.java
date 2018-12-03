package com.example.dubboserver.common;

import com.example.dubboserver.response.BaseResponse;

public interface MessageProcess {

    BaseResponse process(Object message);
}
