package com.example.lenovo.work11_boss.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.work11_boss.R;
import com.example.lenovo.work11_boss.Until.DateUntil;
import com.example.lenovo.work11_boss.bean.Fragment_List_Bean;

import java.util.List;

public class List_AllOrder_Second_Adapter extends RecyclerView.Adapter<List_AllOrder_Second_Adapter.ViewHolder> {
            private Context mContext;
            private List<Fragment_List_Bean.OrderListBean>mjihe;

    public List_AllOrder_Second_Adapter(Context context, List<Fragment_List_Bean.OrderListBean> mjihe) {
        mContext = context;
        this.mjihe = mjihe;
    }

    public void setMjihe(List<Fragment_List_Bean.OrderListBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.list_allorder_second_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
                //快递单号
            viewHolder.secondCode.setText(mjihe.get(i).getOrderId());
            //时间
        String dateToString = DateUntil.DateUtils.getDateToString(mjihe.get(i).getOrderTime());
            viewHolder.secondTime.setText(dateToString);
        //recyclerView
        //布局
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.secondRecycler.setLayoutManager(manager);
        //适配器
        List_AllOrder_Last_Adapter last_adapter=new List_AllOrder_Last_Adapter(mContext,mjihe.get(i).getDetailList());
            viewHolder.secondRecycler.setAdapter(last_adapter);
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView secondCode,secondTime;
        RecyclerView secondRecycler;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            secondCode=itemView.findViewById(R.id.secondCode);
            secondTime=itemView.findViewById(R.id.secondTime);
            secondRecycler=itemView.findViewById(R.id.secondRecycler);
        }
    }
}
