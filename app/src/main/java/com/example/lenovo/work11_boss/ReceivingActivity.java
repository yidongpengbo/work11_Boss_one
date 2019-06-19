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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.work11_boss.Until.APis;
import com.example.lenovo.work11_boss.adapter.Receiving_Address_Adapter;
import com.example.lenovo.work11_boss.adapter.Receiving_Address_Other_Adapter;
import com.example.lenovo.work11_boss.adapter.Receiving_Recycler_Adapter;
import com.example.lenovo.work11_boss.bean.FALB;
import com.example.lenovo.work11_boss.bean.Find_Address_List_Bean;
import com.example.lenovo.work11_boss.bean.Fragment_Shop_Car_Bean;
import com.example.lenovo.work11_boss.bean.ReceivingBean;
import com.example.lenovo.work11_boss.bean.RegistBean;
import com.example.lenovo.work11_boss.iprisenter.IPrenserterImp;
import com.example.lenovo.work11_boss.iview.IView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lenovo
 * 提交订单项
 */
public class ReceivingActivity extends AppCompatActivity implements View.OnClickListener,IView {
    /**
     * 暂无收货地址 点击添加
     */
    private Button mReceivingAddAddress;
    private RecyclerView mReceivingRecycler;
    /**
     * 2
     */
    private TextView mReceivingAllNum;
    /**
     * 840.00
     */
    private TextView mReceivingAllPrice;
    /**
     * 提交订单
     */
    private Button mReceiving;
    private RecyclerView Receiving_Address;
    private Receiving_Address_Adapter mReceivingAddressAdapter;
    ImageView Receiving_Other;
    private RecyclerView Receiving_Address_Other;
    IPrenserterImp mIPrenserterImp;
    private Receiving_Address_Other_Adapter mOtherAdapter;
    //提交订单时的参数
    private int mCommodityId;
    private int mId;
    private Receiving_Recycler_Adapter mRecyclerAdapter;
    List<Fragment_Shop_Car_Bean.ResultBean> mList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initimmersive();
        setContentView(R.layout.activity_receiving);
        initView();
        //判断是否已经注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }
    int num=0;
    double price=0.00;

    /**
     * 商品列表
     * @param bean
     */
    @Subscribe(sticky = true)
    public void onTouch(List<Fragment_Shop_Car_Bean.ResultBean> bean) {
                     mList=bean;
        for (int i = 0; i <bean.size() ; i++) {
                num+=bean.get(i).getCount();
                price+=bean.get(i).getPrice()*bean.get(i).getCount();
            mReceivingAllNum.setText(num+"");
            mReceivingAllPrice.setText(price+"");
            //提交订单时用到
            mCommodityId = bean.get(i).getCommodityId();
        }
        //得到mResult
        initRecycler(bean);
    }

    private void initRecycler(List<Fragment_Shop_Car_Bean.ResultBean> bean) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mReceivingRecycler.setLayoutManager(manager);
        //适配器
        mRecyclerAdapter = new Receiving_Recycler_Adapter(this, bean);
        mReceivingRecycler.setAdapter(mRecyclerAdapter);
    }

    private void initView() {
        mReceivingAddAddress = (Button) findViewById(R.id.Receiving_Add_Address);
        mReceivingAddAddress.setOnClickListener(this);
        mReceivingRecycler = (RecyclerView) findViewById(R.id.Receiving_Recycler);
        mReceivingAllNum = (TextView) findViewById(R.id.Receiving_AllNum);
        mReceivingAllPrice = (TextView) findViewById(R.id.Receiving_AllPrice);
        mReceiving = (Button) findViewById(R.id.Receiving);
        mReceiving.setOnClickListener(this);
        Receiving_Address=(RecyclerView) findViewById(R.id.Receiving_Address);
        Receiving_Other=(ImageView) findViewById(R.id.Receiving_Other);
        Receiving_Other.setOnClickListener(this);
        Receiving_Address_Other=(RecyclerView) findViewById(R.id.Receiving_Address_Other);
        mIPrenserterImp=new IPrenserterImp(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.Receiving_Add_Address:
                initAdd_Address();
                break;
            case R.id.Receiving:
                //提交订单
                initReceiving();
                break;
            case R.id.Receiving_Other:
                initOtherAddress();
                break;
        }
    }

    /**
     * 提交定单
     */
    private void initReceiving() {
        Map<String, String> map = new HashMap<>();
        //TODO:数据覆盖
        List<ReceivingBean> receivingBeans=new ArrayList<>();
        for (Fragment_Shop_Car_Bean.ResultBean list:mList) {
        ReceivingBean receivingBean=new ReceivingBean();
        receivingBean.setCommodityId(list.getCommodityId());
        receivingBean.setAmount(list.getCount());
        receivingBeans.add(receivingBean);
        }
        Gson gson = new Gson();
        String toJson = gson.toJson(receivingBeans);
        map.put("orderInfo",toJson);
       map.put("totalPrice",price+"");
       map.put("addressId",mId+"");
        mIPrenserterImp.startRequest(map,APis.CREATE_ORDER,RegistBean.class);

    }

    /**
     * 得到地址列表中的地址
     */
    boolean boo=false;
    private void initOtherAddress() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        Receiving_Address_Other.setLayoutManager(manager);
        //适配器
        mOtherAdapter = new Receiving_Address_Other_Adapter(this);
        Receiving_Address_Other.setAdapter(mOtherAdapter);
        mIPrenserterImp.startGet(APis.ADRESS_LIST,Find_Address_List_Bean.class);
        //判断隐藏显示
        if (boo){
            Receiving_Address_Other.setVisibility(View.INVISIBLE);
        }else {
            Receiving_Address_Other.setVisibility(View.VISIBLE);
        }
        boo=!boo;
    }

    /**
     * 添加地址，跳转到我的收货地址
     */
    private void initAdd_Address() {
        Intent intent = new Intent(this, FindAddressActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 地址集合
     */
    @Subscribe(sticky = true)
    public void onAddress(FALB mFalb) {
        //将数据放入到Receiving_Address
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        Receiving_Address.setLayoutManager(manager);
        //适配器
        List<Find_Address_List_Bean.ResultBean> list = mFalb.getList();
        for (int i = 0; i < list.size(); i++) {
            //收货地址ID，提交订单时用到
            mId = list.get(i).getId();
        }
        mReceivingAddressAdapter = new Receiving_Address_Adapter(this, list);
        Receiving_Address.setAdapter(mReceivingAddressAdapter);
        //地址显示
        Receiving_Address.setVisibility(View.VISIBLE);
        mReceivingAddAddress.setVisibility(View.INVISIBLE);
        Receiving_Other.setVisibility(View.VISIBLE);
    }



    @Override
    public void onStop() {
        super.onStop();
        //当停止时，取消订阅
        EventBus.getDefault().unregister(this);
    }

    /**
     * 得到数据
     * @param o
     */

    @Override
    public void setData(Object o) {
                //得到地址列表中的地址
            if (o instanceof Find_Address_List_Bean){
                final Find_Address_List_Bean findAddressListBean=(Find_Address_List_Bean)o;
                mOtherAdapter.setMjihe(findAddressListBean.getResult());
                Receiving_Address.setVisibility(View.VISIBLE);
                mReceivingAddAddress.setVisibility(View.INVISIBLE);
                Receiving_Other.setVisibility(View.VISIBLE);
                //选择的点击事件
                mOtherAdapter.setCallBack(new Receiving_Address_Other_Adapter.CallBack() {
                    @Override
                    public void call(int i) {
                        Find_Address_List_Bean.ResultBean resultBean = findAddressListBean.getResult().get(i);
                        //将resultBean代替
                        List<Find_Address_List_Bean.ResultBean> beans = new ArrayList<>();
                        beans.add(resultBean);
                        mReceivingAddressAdapter.setMjihe(beans);
                        Receiving_Address.setAdapter(mReceivingAddressAdapter);
                    }
                });
            }
            //创建订单
            if (o instanceof RegistBean){
                RegistBean registBean=(RegistBean)o;
                Toast.makeText(this, ""+registBean.getMessage(), Toast.LENGTH_SHORT).show();
                mRecyclerAdapter.notifyDataSetChanged();

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

