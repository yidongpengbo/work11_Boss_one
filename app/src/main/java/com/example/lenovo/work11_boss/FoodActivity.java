package com.example.lenovo.work11_boss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.lenovo.work11_boss.Until.APis;
import com.example.lenovo.work11_boss.adapter.FoodAdapter;
import com.example.lenovo.work11_boss.bean.FoodBean;
import com.example.lenovo.work11_boss.iprisenter.IPrenserterImp;
import com.example.lenovo.work11_boss.iview.IView;

public class FoodActivity extends AppCompatActivity implements IView {
    private RecyclerView mFootRecycler;
    private FoodAdapter mFoodAdapter;
    IPrenserterImp mIPrenserterImp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        initimmersive();
        mIPrenserterImp=new IPrenserterImp(this);
        initView();

    }

    private void initView() {
        mFootRecycler = (RecyclerView) findViewById(R.id.footRecycler);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mFootRecycler.setLayoutManager(manager);
        //适配器
        mFoodAdapter = new FoodAdapter(this);
        mFootRecycler.setAdapter(mFoodAdapter);
        //网络请求
        int mPage=1;
        int mCount=5;
        mIPrenserterImp.startGet(APis.MAIN_FOOD+"?page="+mPage+"&count="+mCount,FoodBean.class);
    }

    /**
     * d得到数据
     * @param o
     */
    @Override
    public void setData(Object o) {
        if (o instanceof FoodBean){
            FoodBean foodBean=(FoodBean)o;
            if (foodBean.getResult()!=null){
                mFoodAdapter.setMjihe(foodBean.getResult());
            }else {
                Toast.makeText(this, "未请求到数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void setError(String error) {
        Toast.makeText(this, "出错了："+error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 解绑
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIPrenserterImp.onDelet();
    }
    /**
     * TODO：1.沉浸式
     */
    private void initimmersive() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
