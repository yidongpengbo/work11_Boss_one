package com.example.lenovo.work11_boss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.widget.ImageView;

import com.example.lenovo.work11_boss.Until.APis;
import com.example.lenovo.work11_boss.adapter.Find_XRecycler_Adapter;
import com.example.lenovo.work11_boss.bean.Find_XRecycler_Bean;
import com.example.lenovo.work11_boss.iprisenter.IPrenserterImp;
import com.example.lenovo.work11_boss.iview.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindActivity extends AppCompatActivity implements IView {
    /**
     * 我的圈子 TODO:我的圈子以写，报错HTTP400:(或许是没有发不过圈子的缘故......)
     */
    @BindView(R.id.Find_Delete)
    public ImageView mFindDelete;
    @BindView(R.id.Find_XRecycler)
    public XRecyclerView mFindXRecycler;
    private Find_XRecycler_Adapter mFindXRecyclerAdapter;
    IPrenserterImp mIPrenserterImp;
    private List<Find_XRecycler_Bean.ResultBean> mFindXRecyclerBeanResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        ButterKnife.bind(this);
        mIPrenserterImp=new IPrenserterImp(this);
        //数据
        initXRecycler();
    }
    int mPage;
    private void initXRecycler() {
        //布局管理
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mFindXRecycler.setLayoutManager(manager);
        //适配器
        mFindXRecyclerAdapter = new Find_XRecycler_Adapter(this);
        mFindXRecycler.setAdapter(mFindXRecyclerAdapter);
        mFindXRecycler.setPullRefreshEnabled(true);
        mFindXRecycler.setLoadingMoreEnabled(true);
        mFindXRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                    mPage=1;
                    initData();
            }

            @Override
            public void onLoadMore() {
                    initData();
            }
        });
        initData();
    }

    /**
     * 网络请求
     */
    private void initData() {
        int mCount=10;
        mIPrenserterImp.startGet(APis.MAIN_FIND+"?page="+mPage+"&count="+mCount,Find_XRecycler_Bean.class);
    }

    /**
     * 网络请求成功
     * @param o
     */
    @Override
    public void setData(Object o) {
        if (o instanceof Find_XRecycler_Bean){
            Find_XRecycler_Bean findXRecyclerBean=(Find_XRecycler_Bean)o;
            //将获取的值放到适配器
            if (mPage==1){
                mFindXRecyclerAdapter.setMjihe(findXRecyclerBean.getResult());
            }else {
                mFindXRecyclerAdapter.addMjihe(findXRecyclerBean.getResult());
            }
            mPage++;
            mFindXRecycler.refreshComplete();
            mFindXRecycler.loadMoreComplete();

        }
    }

    @Override
    public void setError(String error) {

    }
}
