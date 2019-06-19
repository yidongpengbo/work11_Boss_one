package com.example.lenovo.work11_boss.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.work11_boss.R;
import com.example.lenovo.work11_boss.ReceivingActivity;
import com.example.lenovo.work11_boss.Until.APis;
import com.example.lenovo.work11_boss.adapter.Fragment_Shop_Car_Adapter;
import com.example.lenovo.work11_boss.bean.Fragment_Shop_Car_Bean;
import com.example.lenovo.work11_boss.iprisenter.IPrenserterImp;
import com.example.lenovo.work11_boss.iview.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Shop_Car extends Fragment implements IView, View.OnClickListener {
    private View view;
    private RecyclerView mShopCarRecycler;
    private Fragment_Shop_Car_Adapter mShopCarAdapter;
    IPrenserterImp mIPrenserterImp;
    /**
     * 全选
     */
    private CheckBox mShopCarCheckBoxAll;
    /**
     * 合计:
     */
    private TextView mShopCarTotal;
    /**
     * 去结算
     */
    private Button mShopCarGoTotal;
    private List<Fragment_Shop_Car_Bean.ResultBean> mResult;
    private boolean mIschecked;
    private boolean mChecked;
    private Fragment_Shop_Car_Bean mFragmentShopCarBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_car, container, false);
        mIPrenserterImp = new IPrenserterImp(this);

        initView(view);
        return view;
    }


    private void initView(View view) {
        mShopCarRecycler = (RecyclerView) view.findViewById(R.id.shop_Car_Recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        mShopCarRecycler.setLayoutManager(manager);
        //适配器
        mShopCarAdapter = new Fragment_Shop_Car_Adapter(getActivity());
        mShopCarRecycler.setAdapter(mShopCarAdapter);
        //请求网络
        mIPrenserterImp.startGet(APis.FRAGEMENT_SHOP_CAR, Fragment_Shop_Car_Bean.class);
        mShopCarCheckBoxAll = (CheckBox) view.findViewById(R.id.Shop_Car_CheckBoxAll);
        mShopCarCheckBoxAll.setOnClickListener(this);
        mShopCarTotal = (TextView) view.findViewById(R.id.Shop_Car_Total);
        mShopCarGoTotal = (Button) view.findViewById(R.id.Shop_Car_GoTotal);
        mShopCarGoTotal.setOnClickListener(this);
    }

    @Override
    public void setData(Object o) {
        if (o instanceof Fragment_Shop_Car_Bean) {
            mFragmentShopCarBean = (Fragment_Shop_Car_Bean) o;
            mResult = mFragmentShopCarBean.getResult();
            mShopCarAdapter.setMjihe(mFragmentShopCarBean.getResult());
            mShopCarAdapter.setListener(new Fragment_Shop_Car_Adapter.ShopCallBackListener() {
                @Override
                public void callBack(List<Fragment_Shop_Car_Bean.ResultBean> mlist) {
                    double totalPrice = 0;
                    int num = 0;
                    int totalNum = 0;
                    for (int i = 0; i < mlist.size(); i++) {
                        //1.得到所有商品的总数
                        totalNum += mlist.get(i).getCount();
                        //2.得到选中的商品的价格和数量
                        if (mlist.get(i).isIschecked()) {
                            totalPrice += mlist.get(i).getPrice() * mlist.get(i).getCount();
                            num += mlist.get(i).getCount();
                        }
                    }

                    //判断
                    if (num < totalNum) {
                        //不会全部选中，全部选中按钮是不选状态
                        mShopCarCheckBoxAll.setChecked(false);
                    }else {
                        mShopCarCheckBoxAll.setChecked(true);
                    }
                    //设置总价、总数值
                    mShopCarTotal.setText("¥"+totalPrice);
                    mShopCarGoTotal.setText("去结算("+num+")");
                }
            });
        }
    }

    @Override
    public void setError(String error) {
        Toast.makeText(getActivity(), "error=" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.Shop_Car_CheckBoxAll:
                //全选按钮选中，
                mChecked = mShopCarCheckBoxAll.isChecked();
                CheckBoxAll(mShopCarCheckBoxAll.isChecked());

                break;
            case R.id.Shop_Car_GoTotal:
                //先判断商品是否选中，选中则跳转
                GoTotal();
                if (mIschecked){
                    Intent intent = new Intent(getActivity(), ReceivingActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "请先选中商品", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void GoTotal() {
        List<Fragment_Shop_Car_Bean.ResultBean> bean=new ArrayList<>();
        for (int i = 0; i <mResult.size() ; i++) {
            mIschecked = mResult.get(i).isIschecked();
            if (mResult.get(i).isIschecked()) {
                    Fragment_Shop_Car_Bean.ResultBean resultBean = mResult.get(i);
                    bean.add(resultBean);
                }
        }
        //EventBus
        EventBus.getDefault().postSticky(bean);
    }

    /**
     * 全选按钮选中
     * @param checked
     */
    private void CheckBoxAll(boolean checked) {
            double totalPrice=0;
            int num=0;
        for (int i = 0; i <mResult.size() ; i++) {
            mResult.get(i).setIschecked(checked);
            Log.i("TAG","mjihe="+mResult);
                //计算总价、总数
            totalPrice+=mResult.get(i).getPrice()*mResult.get(i).getCount();
            num+=mResult.get(i).getCount();
        }
        if (checked){
            mShopCarTotal.setText(""+totalPrice);
            mShopCarGoTotal.setText("去结算("+num+")");
        }else {
            mShopCarTotal.setText("¥0.00");
            mShopCarGoTotal.setText("去结算(0)");
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }
}
