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
import com.example.lenovo.work11_boss.bean.Bean;

import java.util.ArrayList;
import java.util.List;

public class RecyclerFationAdapter extends RecyclerView.Adapter<RecyclerFationAdapter.ViewHolder> {
        private Context mContext;
        private List<Bean.ResultBean.MlssBean.CommodityListBeanXX> mjihe;

    public RecyclerFationAdapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Bean.ResultBean.MlssBean.CommodityListBeanXX> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.recycler_fation_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(mjihe.get(i).getMasterPic()).into(viewHolder.Fation_Image);
        viewHolder.Fation_Title.setText(mjihe.get(i).getCommodityName());
        viewHolder.Fation_Price.setText("¥"+mjihe.get(i).getPrice()+"0");

    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView Fation_Image;
            TextView Fation_Title,Fation_Price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Fation_Image=itemView.findViewById(R.id.Fation_Image);
            Fation_Title=itemView.findViewById(R.id.Fation_Title);
            Fation_Price=itemView.findViewById(R.id.Fation_Price);

        }
    }
}
