package com.example.lenovo.work11_boss.Until;

/**
 * @author lenovo
 *          商品详情
 */
public class IncludeUntil {
    /**
     * 1.单例
     */
    private static IncludeUntil instance;
    public static IncludeUntil getInstance(){
        if (instance==null){
            synchronized (IncludeUntil.class){
                instance=new IncludeUntil();
            }
        }
        return instance;
    }
    private IncludeUntil(){

    }

    /**
     *点击商品，访问网络跳转到详情
     */


}
