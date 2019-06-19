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
import com.example.lenovo.work11_boss.bean.Fragment_List_Bean;

import java.util.List;

public class List_Pay_Second_Adapter extends RecyclerView.Adapter<List_Pay_Second_Adapter.ViewHolder> {
        private Context mContext;
        private List<Fragment_List_Bean.OrderListBean.DetailListBean>mjihe;

    public List_Pay_Second_Adapter(Context context, List<Fragment_List_Bean.OrderListBean.DetailListBean> mjihe) {
        mContext = context;
        this.mjihe = mjihe;
    }

    public void setMjihe(List<Fragment_List_Bean.OrderListBean.DetailListBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(mContext).inflate(R.layout.list_pay_second_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        //商品图片
        String image = mjihe.get(i).getCommodityPic().split("\\,")[0];
        Glide.with(mContext).load(image).into(viewHolder.Pay_Image);
        //商品名字
        viewHolder.Pay_Name.setText(mjihe.get(i).getCommodityName());
        //商品单价
        viewHolder.Pay_Price.setText("¥"+mjihe.get(i).getCommodityPrice());

    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Pay_Image;
        TextView Pay_Name,Pay_Price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Pay_Image=itemView.findViewById(R.id.Pay_Image);
            Pay_Name=itemView.findViewById(R.id.Pay_Name);
            Pay_Price=itemView.findViewById(R.id.Pay_Price);

        }
    }
}
