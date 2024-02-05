package com.hocvui.response;

public class SuccessRespon<T> extends BaseRespon<T>{
    public SuccessRespon(T data) {
        super("success",null,data);
    }
    public SuccessRespon(String message, T data) {
        super("success",message,data);
    }
}
