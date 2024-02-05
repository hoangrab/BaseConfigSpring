package com.hocvui.response;

public class ErrorRespon<T> extends BaseRespon{
    public ErrorRespon(String message) {
        super("failed",message,null);
    }

    public ErrorRespon(T data, String message) {
        super("failed",message,data);
    }

    public ErrorRespon(String status,T data, String message) {
        super("failed",message,data);
    }
}
