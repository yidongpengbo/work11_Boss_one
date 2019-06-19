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
import com.example.lenovo.work11_boss.Until.DateUntil;
import com.example.lenovo.work11_boss.bean.FoodBean;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
        private Context mContext;
        private List<FoodBean.ResultBean>mjihe;

    public FoodAdapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<FoodBean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(mContext).inflate(R.layout.foodadapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(mjihe.get(i).getMasterPic()).into(viewHolder.foodImage);
        viewHolder.foodName.setText(mjihe.get(i).getCommodityName());
        viewHolder.foodPrice.setText("¥"+mjihe.get(i).getPrice());
        viewHolder.foodBrowseNum.setText("已浏览"+mjihe.get(i).getBrowseNum()+"次");
        String dateToString = DateUntil.DateUtils.getDateToString(mjihe.get(i).getBrowseTime());
        viewHolder.foodTime.setText(dateToString);
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName,foodPrice,foodBrowseNum,foodTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage=itemView.findViewById(R.id.foodImage);
            foodName=itemView.findViewById(R.id.foodName);
            foodPrice=itemView.findViewById(R.id.foodPrice);
            foodBrowseNum=itemView.findViewById(R.id.foodBrowseNum);
            foodTime=itemView.findViewById(R.id.foodTime);
        }
    }
}
