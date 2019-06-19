package com.example.lenovo.work11_boss.iprisenter;

import android.util.Log;

import com.example.lenovo.work11_boss.Until.MyCallBack;
import com.example.lenovo.work11_boss.imodel.IModelImp;
import com.example.lenovo.work11_boss.iview.IView;

import java.util.Map;

public class IPrenserterImp implements IPresenter {
    IView mIView;
    IModelImp mIModelImp;

    public IPrenserterImp(IView IView) {
        mIView = IView;
        mIModelImp=new IModelImp();
    }

    @Override
    public void startRequest(Map<String, String> map, String path, Class clazz) {
        mIModelImp.requestData(map, path, clazz, new MyCallBack() {
            @Override   //失败
            public void fail(String e) {
                    mIView.setError(e);
            }

            @Override   //成功
            public void success(Object o) {
                    mIView.setData(o);
            }
        });

    }
        //TODO:qqqqqqqq
    @Override
    public void startGet(String path, Class clazz) {
        mIModelImp.requestGet(path, clazz, new MyCallBack() {
            @Override
            public void fail(String e) {
                    mIView.setError(e);
            }

            @Override
            public void success(Object o) {
                    mIView.setData(o);
            }
        });
    }

    @Override
    public void startPut(Map<String, String> map, String path, Class clazz) {
        mIModelImp.requestPut(map, path, clazz, new MyCallBack() {
            @Override
            public void fail(String e) {
                Log.i("TAG","我水电费VB"+e);
                mIView.setError(e);
            }

            @Override
            public void success(Object o) {
                Log.i("TAG","wedfwdf"+o);
                mIView.setData(o);
            }
        });
    }

    @Override
    public void startImage(String path, Map<String, String> map, Class clazz) {
        mIModelImp.requestImage(path, map, clazz, new MyCallBack() {
            @Override
            public void fail(String e) {
                mIView.setError(e);
            }

            @Override
            public void success(Object o) {
                mIView.setData(o);
            }
        });
    }


    /**
     * 解绑
     */
    public void onDelet(){
        if (mIModelImp!=null){
            mIModelImp=null;
        }
        if (mIView!=null){
            mIView=null;
        }
    }
}
