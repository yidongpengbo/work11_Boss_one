package com.example.lenovo.work11_boss.home_viewpager_pagertransformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author lenovo
 *
 */
public class MyGallyPageTransformer implements ViewPager.PageTransformer {
                //最小规模
        private static final float min_scale=0.85f;

    @Override   //TODO:变换页面
    public void transformPage(@NonNull View view, float v) {
        //
        float scaleFactor = Math.max(min_scale, 1 - Math.abs(v));
        if (v<-1){
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        }else if (v<0){
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        }else if (v >= 0 && v < 1) {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        } else if (v >= 1) {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        }

    }
}
