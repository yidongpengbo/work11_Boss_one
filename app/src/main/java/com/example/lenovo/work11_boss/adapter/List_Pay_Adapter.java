package com.example.lenovo.work11_boss.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.work11_boss.R;
import com.example.lenovo.work11_boss.ToPayActivity;
import com.example.lenovo.work11_boss.Until.DateUntil;
import com.example.lenovo.work11_boss.bean.Fragment_List_Bean;

import java.util.ArrayList;
import java.util.List;

public class List_Pay_Adapter extends RecyclerView.Adapter<List_Pay_Adapter.ViewHolder> {
        private Context mContext;
        private List<Fragment_List_Bean.OrderListBean>mjihe;

    public List_Pay_Adapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Fragment_List_Bean.OrderListBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.list_pay_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        //单号
                    viewHolder.orderCode.setText(mjihe.get(i).getOrderId());
        //时间
                    String toString = DateUntil.DateUtils.getDateToString(mjihe.get(i).getOrderTime());
        viewHolder.payTime.setText(toString);
        //recycler
        List_Pay_Second_Adapter secondAdapter = new List_Pay_Second_Adapter(mContext,mjihe.get(i).getDetailList());
            viewHolder.payRecycler.setAdapter(secondAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.payRecycler.setLayoutManager(manager);
        //去支付的点击事件
        viewHolder.toPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到支付页
                Intent intent = new Intent(mContext, ToPayActivity.class);
                intent.putExtra("orderID",mjihe.get(i).getOrderId());
                List<Fragment_List_Bean.OrderListBean.DetailListBean> detailList = mjihe.get(i).getDetailList();
                    double AllPrice=0;
                for (int j = 0; j <detailList.size() ; j++) {
                    AllPrice+=detailList.get(j).getCommodityPrice()*detailList.get(j).getCommodityCount();
                }
                intent.putExtra("price",AllPrice);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            TextView orderCode,payTime;
            RecyclerView payRecycler;
            Button toPay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderCode=itemView.findViewById(R.id.orderCode);
            payTime=itemView.findViewById(R.id.payTime);
            payRecycler=itemView.findViewById(R.id.payRecycler);
            toPay=itemView.findViewById(R.id.toPay);
        }
    }
}
