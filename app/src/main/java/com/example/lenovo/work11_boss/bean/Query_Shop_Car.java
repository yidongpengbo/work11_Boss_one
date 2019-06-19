package com.example.lenovo.work11_boss.bean;

public class Query_Shop_Car {
    public int commodityId;
    public int count;
    //有参
    public Query_Shop_Car(int commodityId, int count) {
        this.commodityId = commodityId;
        this.count = count;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
