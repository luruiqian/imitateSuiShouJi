package com.cashbook.cashbook.retrofit.bean;

public class BaseRequest<T> {
    public int status;
    public String message;
    public int offset;
    public T data;
}
