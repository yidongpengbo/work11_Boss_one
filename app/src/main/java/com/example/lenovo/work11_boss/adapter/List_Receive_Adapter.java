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
import com.example.lenovo.work11_boss.ReceiveOkActivity;
import com.example.lenovo.work11_boss.Until.DateUntil;
import com.example.lenovo.work11_boss.bean.Fragment_List_Bean;

import java.util.ArrayList;
import java.util.List;

public class List_Receive_Adapter extends RecyclerView.Adapter<List_Receive_Adapter.ViewHolder> {
    private Context mContext;
      private   List<Fragment_List_Bean.OrderListBean> mjihe;

    public List_Receive_Adapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Fragment_List_Bean.OrderListBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public List_Receive_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.list_receive_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List_Receive_Adapter.ViewHolder viewHolder, final int i) {
        //订单号
        viewHolder.ReceiveCode.setText(mjihe.get(i).getOrderId());
        //时间
        String toString = DateUntil.DateUtils.getDateToString(mjihe.get(i).getOrderTime());
        viewHolder.ReceiveTime.setText(toString);
        //派送公司
        viewHolder.ReceiveExpress.setText(mjihe.get(i).getExpressCompName());
        //快递单号
        viewHolder.ReceiveExpressSn.setText(mjihe.get(i).getExpressSn());
        //确认收货点击事件
        viewHolder.ReceiveOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderId = mjihe.get(i).getOrderId();
                Intent intent = new Intent(mContext, ReceiveOkActivity.class);
                    intent.putExtra("ORDERID",orderId);
                    mContext.startActivity(intent);


            }
        });
        //recycler
        //布局
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.ReceiveRecycler.setLayoutManager(manager);
        //适配器
        List_Receive_Second_Adapter secondAdapter = new List_Receive_Second_Adapter(mContext,mjihe.get(i).getDetailList());
        viewHolder.ReceiveRecycler.setAdapter(secondAdapter);
    }


    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ReceiveCode,ReceiveTime,ReceiveExpress,ReceiveExpressSn;
        RecyclerView ReceiveRecycler;
        Button ReceiveOK;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ReceiveCode=itemView.findViewById(R.id.ReceiveCode);
            ReceiveTime=itemView.findViewById(R.id.ReceiveTime);
            ReceiveExpress=itemView.findViewById(R.id.ReceiveExpress);
            ReceiveExpressSn=itemView.findViewById(R.id.ReceiveExpressSn);
            ReceiveRecycler=itemView.findViewById(R.id.ReceiveRecycler);
            ReceiveOK=itemView.findViewById(R.id.ReceiveOK);
        }
    }
}
