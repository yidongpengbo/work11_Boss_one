package com.example.lenovo.work11_boss.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.work11_boss.R;
import com.example.lenovo.work11_boss.Until.Shop_Car_Add_Reduce_View;
import com.example.lenovo.work11_boss.bean.Fragment_List_Bean;

import java.util.List;

/**
 * @author lenovo
 * 全部订单的最后一个Recycler
 */
public class List_AllOrder_Last_Adapter extends RecyclerView.Adapter<List_AllOrder_Last_Adapter.ViewHolder> {
        private Context mContext;
        private List<Fragment_List_Bean.OrderListBean.DetailListBean>mjihe;

    public List_AllOrder_Last_Adapter(Context context, List<Fragment_List_Bean.OrderListBean.DetailListBean> jihe) {
        mContext = context;
        mjihe = jihe;
    }

    public void setMjihe(List<Fragment_List_Bean.OrderListBean.DetailListBean> jihe) {
        mjihe = jihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.list_allorder_last_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String image = mjihe.get(i).getCommodityPic().split("\\,")[0];
        Glide.with(mContext).load(image).into(viewHolder.lastImage);
        viewHolder.lastName.setText(mjihe.get(i).getCommodityName());
        viewHolder.lastPrice.setText("¥"+mjihe.get(i).getCommodityPrice());
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView lastImage;
        TextView lastName,lastPrice;
        Shop_Car_Add_Reduce_View lastAddReduce;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lastImage=itemView.findViewById(R.id.lastImage);
            lastName=itemView.findViewById(R.id.lastName);
            lastPrice=itemView.findViewById(R.id.lastPrice);
            lastAddReduce=itemView.findViewById(R.id.lastAddReduce);
        }
    }
}
