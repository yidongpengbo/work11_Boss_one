package com.example.lenovo.work11_boss.imodel;

import android.util.Log;

import com.example.lenovo.work11_boss.Until.MyCallBack;
import com.example.lenovo.work11_boss.Until.OKHttpUntil;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;

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
    public void requestGet(final String path, final Class clazz, MyCallBack myCallBack) {
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
                    Log.i("TAG","path="+path);
                    Object o = gson.fromJson(data, clazz);
                    if (mMyCallBack!=null){
                        mMyCallBack.success(o);
                    }
                }
            });
    }

    /**
     * put请求
     * @param map
     * @param path
     * @param clazz
     * @param myCallBack
     */
    @Override
    public void requestPut(Map<String, String> map, String path, final Class clazz, MyCallBack myCallBack) {
        Map<String, RequestBody> body = OKHttpUntil.getInstance().generateRequestBody(map);
        OKHttpUntil.getInstance().put(path, body, new OKHttpUntil.CallBack() {
            @Override
            public void fail(String error) {
                if (mMyCallBack!=null){
                    mMyCallBack.fail(error);
                    Log.i("TAG","轻微的"+error);
                }
            }

            @Override
            public void success(String data) {
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
                if (mMyCallBack!=null){
                    mMyCallBack.success(o);
                    Log.i("TAG","用户GV的"+o);
                }
            }
        });
    }

    @Override
    public void requestImage(String path, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
            OKHttpUntil.getInstance().upImage(path, map, new OKHttpUntil.CallBack() {
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
