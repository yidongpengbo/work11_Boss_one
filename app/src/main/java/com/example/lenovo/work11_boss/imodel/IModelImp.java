package com.example.lenovo.work11_boss.imodel;

import com.example.lenovo.work11_boss.Until.MyCallBack;
import com.example.lenovo.work11_boss.Until.OKHttpUntil;
import com.google.gson.Gson;

import java.util.Map;

public class IModelImp implements IModel {
        MyCallBack mMyCallBack;
    @Override
    public void requestData(Map<String, String> map, String path, final Class clazz, final MyCallBack myCallBack) {
                mMyCallBack=myCallBack;
        /**
         * TODO：post请求
         */
        OKHttpUntil.getInstance().post(path, map, new OKHttpUntil.CallBack() {
            @Override
            public void fail(String error) {
                    if (mMyCallBack!=null){
                        mMyCallBack.fail(error);
                    }
            }

            @Override
            public void success(String data) {
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
               if (mMyCallBack!=null){
                   mMyCallBack.success(o);
               }
            }
        });
    }

    /**
     * TODO:qqqqqqqqqqqqqqqqqqqq
     * @param path
     * @param myCallBack
     */
    @Override
    public void requestGet(String path, final Class clazz, MyCallBack myCallBack) {
               this.mMyCallBack=myCallBack;
            OKHttpUntil.getInstance().get(path, new OKHttpUntil.CallBack() {
                @Override
                public void fail(String error) {
                    if (mMyCallBack!=null){
                        mMyCallBack.fail(error);
                    }
                }

                @Override
                public void success(String data) {
                    Gson gson = new Gson();
                    Object o = gson.fromJson(data, clazz);
                    if (mMyCallBack!=null){
                        mMyCallBack.success(o);
                    }
                }
            });
    }


}
