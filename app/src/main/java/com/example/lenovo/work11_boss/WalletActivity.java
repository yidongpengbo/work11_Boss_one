package com.example.lenovo.work11_boss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.work11_boss.Until.APis;
import com.example.lenovo.work11_boss.adapter.WalletAdapter;
import com.example.lenovo.work11_boss.bean.Find_Wallet_Bean;
import com.example.lenovo.work11_boss.iprisenter.IPrenserterImp;
import com.example.lenovo.work11_boss.iview.IView;

import java.util.List;

public class WalletActivity extends AppCompatActivity implements IView {
    private TextView mWalletBalance;
    private RecyclerView mWalletRecycler;
    private IPrenserterImp mIPrenserterImp;
    private WalletAdapter mWalletAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        initimmersive();
        initView();
        mIPrenserterImp = new IPrenserterImp(this);
        //网络请求
        int mPage=1;
        int mCount=1;
        mIPrenserterImp.startGet(APis.MAIN_WALLET+"?page="+mPage+"&count="+mCount,Find_Wallet_Bean.class);
    }

    private void initView() {
        //当前余额
        mWalletBalance = (TextView) findViewById(R.id.walletBalance);
        mWalletRecycler = (RecyclerView) findViewById(R.id.walletRecycler);
        //布局
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mWalletRecycler.setLayoutManager(manager);
        //适配器
        mWalletAdapter = new WalletAdapter(this);
        mWalletRecycler.setAdapter(mWalletAdapter);

    }

    /**
     * 得到数据
     * @param o
     */
    @Override
    public void setData(Object o) {
            if (o instanceof Find_Wallet_Bean){
                Find_Wallet_Bean find_wallet_bean=(Find_Wallet_Bean)o;
                Find_Wallet_Bean.ResultBean result = find_wallet_bean.getResult();
                double balance = result.getBalance();
                mWalletBalance.setText(balance+"");
                Log.i("TAG",balance*100000+"");
                List<Find_Wallet_Bean.ResultBean.DetailListBean> detailList = result.getDetailList();
                mWalletAdapter.setMjihe(detailList);
                if (detailList.size()<=0){
                    Toast.makeText(this,"您还未进行消费哦",Toast.LENGTH_LONG).show();
                }
            }
    }

    @Override
    public void setError(String error) {
        Toast.makeText(this, "error="+error, Toast.LENGTH_SHORT).show();
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
