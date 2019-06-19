package com.example.lenovo.work11_boss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.work11_boss.Until.APis;
import com.example.lenovo.work11_boss.adapter.Find_Address_Adapter;
import com.example.lenovo.work11_boss.bean.FALB;
import com.example.lenovo.work11_boss.bean.Find_Address_List_Bean;
import com.example.lenovo.work11_boss.iprisenter.IPrenserterImp;
import com.example.lenovo.work11_boss.iview.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class FindAddressActivity extends AppCompatActivity implements View.OnClickListener,IView {
    /**
     * 我的收货地址
     */
    private TextView mMy;
    /**
     * 完成
     */
    private TextView mAddAddressOK;
    private RecyclerView mAddAddressRecycler;
    /**
     * 新增收货地址
     */
    private Button mAddAddressButton;
    private Find_Address_Adapter mAddressAdapter;
    IPrenserterImp mIPrenserterImp;
    /**
     * 建立接收的bean
     */
    private FALB mFalb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_address);
        initimmersive();
        mIPrenserterImp=new IPrenserterImp(this);
        initView();
        initRecycler();
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mAddAddressRecycler.setLayoutManager(manager);
        //适配器
        mAddressAdapter = new Find_Address_Adapter(this);
        mAddAddressRecycler.setAdapter(mAddressAdapter);
        //网络请求
        mIPrenserterImp.startGet(APis.ADRESS_LIST,Find_Address_List_Bean.class);
    }

    private void initView() {
        mMy = (TextView) findViewById(R.id.my);
        mAddAddressOK = (TextView) findViewById(R.id.Add_Address_OK);
        mAddAddressOK.setOnClickListener(this);
        mAddAddressRecycler = (RecyclerView) findViewById(R.id.Add_Address_Recycler);
        mAddAddressButton = (Button) findViewById(R.id.Add_Address_Button);
        mAddAddressButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.Add_Address_OK:
                //TODO：跳转到提交订单，将选中的值放入到提交订单
                    initAddressOk();
                break;
            case R.id.Add_Address_Button:
                initAddress();
                break;
        }
    }

    /**
     * TODO：跳转到提交订单，将选中的值放入到提交订单
     */
    private void initAddressOk() {
        if (mFalb!=null) {
            EventBus.getDefault().postSticky(mFalb);
            Intent intent = new Intent(this, ReceivingActivity.class);
            startActivity(intent);
            finish();
        }else {
            finish();
        }
    }

    /**
     * TODO:新增收货地址
     */
    private void initAddress() {
        Intent intent = new Intent(this, Add_Adress_Activity.class);
        startActivity(intent);
    }

    /**
     * 获取数据成功
     * @param o
     */
    @Override
    public void setData(Object o) {
        if (o instanceof Find_Address_List_Bean){
            Find_Address_List_Bean findAddressListBean=(Find_Address_List_Bean)o;
            String message = findAddressListBean.getMessage();
            if(!message.equals("查询成功")){
                Toast.makeText(this, "还没有收货地址，快去添加吧", Toast.LENGTH_SHORT).show();
                mAddressAdapter.notifyDataSetChanged();
            }else {
                mAddressAdapter.setMjihe(findAddressListBean.getResult());
                mAddressAdapter.setCheckbox(new Find_Address_Adapter.Checkbox() {
                    @Override
                    public void check(List<Find_Address_List_Bean.ResultBean> mjihe,int i) {
                        //将选中的列表集合数据添加到ReceivingActivity
                        mFalb = new FALB();
                        List<Find_Address_List_Bean.ResultBean>  mlist=new ArrayList<>();
                        mlist.add(mjihe.get(i));
                        mFalb.setList(mlist);


                    }
                });
            }
        }
    }

    /**
     * 获取数据失败
     * @param error
     */
    @Override
    public void setError(String error) {
        Toast.makeText(this, "error="+error, Toast.LENGTH_SHORT).show();
        return;
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
