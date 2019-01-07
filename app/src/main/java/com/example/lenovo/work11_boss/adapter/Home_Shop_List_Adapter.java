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
import com.example.lenovo.work11_boss.bean.Home_Shop_List_Bean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Home_Shop_List_Adapter extends XRecyclerView.Adapter<Home_Shop_List_Adapter.ViewHolder> {
            private Context mContext;
            private List<Home_Shop_List_Bean.ResultBean>mjihe;

    public Home_Shop_List_Adapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Home_Shop_List_Bean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }
    public void addMjihe(List<Home_Shop_List_Bean.ResultBean> jihe){
        mjihe.addAll(jihe);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.home_shop_list_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(mjihe.get(i).getMasterPic()).into(viewHolder.Shop_Image);
        viewHolder.Shop_Title.setText(mjihe.get(i).getCommodityName());
        viewHolder.Shop_Price.setText("¥"+mjihe.get(i).getPrice());
        viewHolder.Shop_Sell.setText("已售"+mjihe.get(i).getSaleNum()+"件");
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
                ImageView Shop_Image;
                TextView Shop_Title,Shop_Price,Shop_Sell;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Shop_Image=itemView.findViewById(R.id.Shop_Image);
            Shop_Title=itemView.findViewById(R.id.Shop_Title);
            Shop_Price=itemView.findViewById(R.id.Shop_Price);
            Shop_Sell=itemView.findViewById(R.id.Shop_Sell);
        }
    }
}
