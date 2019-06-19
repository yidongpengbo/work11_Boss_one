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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.work11_boss.R;
import com.example.lenovo.work11_boss.Until.APis;
import com.example.lenovo.work11_boss.adapter.List_AllOrder_Adapter;
import com.example.lenovo.work11_boss.adapter.List_Comment_First_Adapter;
import com.example.lenovo.work11_boss.adapter.List_Pay_Adapter;
import com.example.lenovo.work11_boss.adapter.List_Receive_Adapter;
import com.example.lenovo.work11_boss.bean.Fragment_List_Bean;
import com.example.lenovo.work11_boss.iprisenter.IPrenserterImp;
import com.example.lenovo.work11_boss.iview.IView;

import java.util.List;

public class Fragment_List extends Fragment implements IView {
    public TextView mOrderTime;

    //待付款
    public RecyclerView mFragmentListPayRecycler;
    //全部订单
    public RecyclerView mFragmentListAllOrderRecycler;
    //待评价
    public RecyclerView mShopCarCommentFirstRecycler;
    //待收货
    private RecyclerView mReceiveRecycler;
    IPrenserterImp mIPrenserterImp;
    private List_Pay_Adapter mPayAdapter;
    private List_Comment_First_Adapter mCommentFirstAdapter;
    private ImageView mPayImage;
    private ImageView mReceiveImage;
    private ImageView mCommentImage;
    private ImageView mAll_listImage;
    //全部订单的适配器
    private List_AllOrder_Adapter mOrderAdapter;
    private Button mToPay;
    private Button mCancellation;
    private String mOrderId;
    private int mPrice;
    private List_Receive_Adapter mReceiveAdapter;
    private int mMstatus;
    private Button mReceiveOK;
    private String mOrderId1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_car, container, false);
        //不能用ButterKnife???
        mIPrenserterImp=new IPrenserterImp(this);
        mFragmentListPayRecycler=view.findViewById(R.id.Fragment_List_Pay_Recycler);
        //待付款、返回、去支付
        mPayImage = (ImageView) view.findViewById(R.id.payImage);
        mCancellation = (Button)view.findViewById(R.id.Cancellation);
        mToPay = (Button)view.findViewById(R.id.toPay);
        //待收货、
        mReceiveImage=(ImageView)view.findViewById(R.id.receiveImage);
        //待评价
        mCommentImage=(ImageView)view.findViewById(R.id.commentImage);
        //待评价
        mShopCarCommentFirstRecycler=view.findViewById(R.id.Shop_Car_Comment_First_Recycler);
        //待收货
        mReceiveRecycler = view.findViewById(R.id.Fragment_List_Receive_Recycler);
        //时间
        mAll_listImage = (ImageView)view.findViewById(R.id.all_listImage);
        mFragmentListAllOrderRecycler=view.findViewById(R.id.List_AllOrder_Recycler);

        //待付款的点击事件
        initpayImage();
        //initToPay();
        //全部订单
        initall_listImage();
        //待评价
        ShopComment();
        //待收货
        initReceive();
        return view;
    }


    /**
     * TODO:全部订单的点击事件
     */
    private void initall_listImage() {
        mAll_listImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    //布局
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(OrientationHelper.VERTICAL);
                mFragmentListAllOrderRecycler.setLayoutManager(manager);
                //适配器
                mOrderAdapter = new List_AllOrder_Adapter(getActivity());
              mFragmentListAllOrderRecycler.setAdapter(mOrderAdapter);
              //网络请求
                mMstatus=0;
                int page=1;
                int count=10;
                //网络请求
               mIPrenserterImp.startGet(APis.FRAGMENT_LIST+"status="+mMstatus+"&page="+page+"&count="+count,Fragment_List_Bean.class);
            }
        });
    }

    /**
     * TODO：待付款的点击事件
     */
    private void initpayImage() {
        mPayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //显示
                mFragmentListPayRecycler.setVisibility(View.VISIBLE);
                mReceiveRecycler.setVisibility(View.INVISIBLE);
                //隐藏全部订单、待评价
                mFragmentListAllOrderRecycler.setVisibility(View.INVISIBLE);
                mShopCarCommentFirstRecycler.setVisibility(View.INVISIBLE);
                //弹出Recycler
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(OrientationHelper.VERTICAL);
                mFragmentListPayRecycler.setLayoutManager(manager);
                //适配器
                mPayAdapter = new List_Pay_Adapter(getActivity());
                mFragmentListPayRecycler.setAdapter(mPayAdapter);
                mMstatus=1;
                int page=1;
                int count=10;
                //网络请求
                mIPrenserterImp.startGet(APis.FRAGMENT_LIST+"status="+mMstatus+"&page="+page+"&count="+count,Fragment_List_Bean.class);


            }
        });
    }

    /**
     * TODO:待收货的点击事件
     */
    public void initReceive(){
        mReceiveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //布局
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(OrientationHelper.VERTICAL);
                mReceiveRecycler.setLayoutManager(manager);
             //适配器
                mReceiveAdapter = new List_Receive_Adapter(getActivity());
                mReceiveRecycler.setAdapter(mReceiveAdapter);
               //显示
                mReceiveRecycler.setVisibility(View.VISIBLE);
                mFragmentListPayRecycler.setVisibility(View.INVISIBLE);
               // mCancellation.setVisibility(View.INVISIBLE);
               // mToPay.setVisibility(View.INVISIBLE);
                mShopCarCommentFirstRecycler.setVisibility(View.INVISIBLE);
                mMstatus=2;
                int page=1;
                int count=10;
                //网络请求
                mIPrenserterImp.startGet(APis.FRAGMENT_LIST+"status="+mMstatus+"&page="+page+"&count="+count,Fragment_List_Bean.class);


            }
        });
    }

    /**
     * TODO:待评价的点击事件
     */
    public void ShopComment(){
        mCommentImage.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                //显示待评价
                mShopCarCommentFirstRecycler.setVisibility(View.VISIBLE);
                //隐藏其他
                mFragmentListPayRecycler.setVisibility(View.INVISIBLE);
               // mCancellation.setVisibility(View.INVISIBLE);
                //mToPay.setVisibility(View.INVISIBLE);
                mReceiveRecycler.setVisibility(View.INVISIBLE);
//                mFragmentListAllOrderRecycler.setVisibility(View.INVISIBLE);
                //布局
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(OrientationHelper.VERTICAL);
                mShopCarCommentFirstRecycler.setLayoutManager(manager);
                //适配器
                mCommentFirstAdapter = new List_Comment_First_Adapter(getActivity());
                mShopCarCommentFirstRecycler.setAdapter(mCommentFirstAdapter);
                //网络请求
                mMstatus = 3;
                int page=1;
                int count=10;
                //网络请求
                mIPrenserterImp.startGet(APis.FRAGMENT_LIST+"status="+ mMstatus +"&page="+page+"&count="+count,Fragment_List_Bean.class);
            }
        });

    }



    /**
     * TODO:获取数据
     * @param o
     */
    @Override
    public void setData(Object o) {
        if (o instanceof Fragment_List_Bean){
            Fragment_List_Bean fragmentShopCarBean=(Fragment_List_Bean)o;
            List<Fragment_List_Bean.OrderListBean> orderList = fragmentShopCarBean.getOrderList();

            //判断
            if (mMstatus==1){
            for (int i = 0; i <orderList.size() ; i++) {
                List<Fragment_List_Bean.OrderListBean.DetailListBean> detailList = orderList.get(i).getDetailList();
                //TODO:待付款
                mPayAdapter.setMjihe(orderList);
                mOrderId = orderList.get(i).getOrderId();
                for (int j = 0; j < detailList.size(); j++) {
                    mPrice = detailList.get(j).getCommodityCount() * detailList.get(j).getCommodityPrice();
                }

            }
            }else if (mMstatus==2){
                //TODO:待收货
                mReceiveAdapter.setMjihe(orderList);
                for (int i = 0; i <orderList.size() ; i++) {
                    mOrderId1 = orderList.get(i).getOrderId();
                }
            }else if (mMstatus==3){
                //TODO：待评价
                mCommentFirstAdapter.setMjihe(orderList);
            }else if (mMstatus==0){
                //TODO:全部订单!!!!!错呀错
                   mOrderAdapter.setMjihe(orderList);
            }
        }

    }

    @Override
    public void setError(String error) {
        Toast.makeText(getActivity(), "出错啦，原因：未请求到数据"+error, Toast.LENGTH_SHORT).show();
    }
}
