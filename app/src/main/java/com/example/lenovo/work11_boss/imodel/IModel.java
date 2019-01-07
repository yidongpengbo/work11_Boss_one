package com.example.lenovo.work11_boss.imodel;

import com.example.lenovo.work11_boss.Until.MyCallBack;

import java.util.Map;

public interface IModel {
    void requestData(Map<String,String>map, String path, Class clazz, MyCallBack myCallBack);
        //TODO:qqqqqqqqqqq
    void requestGet(String path,Class clazz,MyCallBack myCallBack);
}
