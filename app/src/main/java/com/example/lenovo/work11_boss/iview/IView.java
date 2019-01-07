package com.example.lenovo.work11_boss.iview;

public interface IView<T> {
    void setData(T t);
    void setError(String error);
}
