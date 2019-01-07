package com.example.lenovo.work11_boss.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.work11_boss.R;
import com.example.lenovo.work11_boss.Until.APis;
import com.example.lenovo.work11_boss.adapter.Find_Recycler_Adapter;
import com.example.lenovo.work11_boss.bean.Find_XRecycler_Bean;
import com.example.lenovo.work11_boss.iprisenter.IPrenserterImp;
import com.example.lenovo.work11_boss.iview.IView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_Find extends Fragment implements IView {
    @BindView(R.id.Find_Recycler)
    RecyclerView FindRecycler;
    private Find_Recycler_Adapter mFindRecyclerAdapter;
    IPrenserterImp mIPrenserterImp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_find,container,false);
        ButterKnife.bind(this,view);
        mIPrenserterImp=new IPrenserterImp(this);
        //设置Recycler
        initRecycler(view);
        return view;
    }

    private void initRecycler(View view) {
        //展示布局格式
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        FindRecycler.setLayoutManager(manager);
        //适配器
        mFindRecyclerAdapter = new Find_Recycler_Adapter(getActivity());
        FindRecycler.setAdapter(mFindRecyclerAdapter);
        //网络请求
       // page=1&count=1
         mIPrenserterImp.startGet(APis.FIND_PATH+"?page="+1+"&count="+10,Find_XRecycler_Bean.class);
    }

    /**
     * 得到数据
     * @param o
     */
    @Override
    public void setData(Object o) {
            if (o instanceof Find_XRecycler_Bean){
                Find_XRecycler_Bean findRecyclerBean=(Find_XRecycler_Bean)o;
                mFindRecyclerAdapter.setMjihe(findRecyclerBean.getResult());
            }
    }

    @Override
    public void setError(String error) {

    }
}
