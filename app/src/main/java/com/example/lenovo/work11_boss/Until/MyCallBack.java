package com.example.lenovo.work11_boss.Until;

public interface MyCallBack<T> {
    //接收的是String类型
    void fail(String e);
    void success(T t);
}
