package com.example.lenovo.work11_boss.iprisenter;

import java.util.Map;

public interface IPresenter {
    void startRequest(Map<String,String>map,String path,Class clazz);

    void startGet(String path,Class clazz);
    void startPut(Map<String,String>map,String path,Class clazz);
    void startImage(String path,Map<String,String>map,Class clazz);
}
