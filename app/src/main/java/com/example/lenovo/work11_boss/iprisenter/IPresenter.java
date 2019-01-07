package com.example.lenovo.work11_boss.iprisenter;

import java.util.Map;

public interface IPresenter {
    void startRequest(Map<String,String>map,String path,Class clazz);
    //TODO:qqqqqqq
    void startGet(String path,Class clazz);
}
