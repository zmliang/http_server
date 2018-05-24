package com.zml.app.user.util;

import io.netty.handler.codec.http.HttpResponseStatus;

public class ErrorCode {

    public static final int EC_OK = 0;                    //Authorization Request Code ...
    public static final int EC_BAD_REQUEST = 1;
    public static final int EC_UNAUTHORIZATED = 2;
    public static final int EC_EXCETION = 3;
    public static final int EC_PATH_NOT_FOUND = 4;


    public static HttpResponseStatus errorToHttpResponseStatus(int errorCode) {
        switch (errorCode) {
            case EC_OK:
                return HttpResponseStatus.OK;
            case EC_BAD_REQUEST:
                return HttpResponseStatus.BAD_REQUEST;
            case EC_UNAUTHORIZATED:
                return HttpResponseStatus.UNAUTHORIZED;
            case EC_PATH_NOT_FOUND:
                return HttpResponseStatus.NOT_FOUND;
            default:
                return HttpResponseStatus.INTERNAL_SERVER_ERROR;
        }
    }


}
