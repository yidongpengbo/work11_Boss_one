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

public class RecyclerHotAdapter extends RecyclerView.Adapter<RecyclerHotAdapter.ViewHolder> {
        private Context mContext;
        private List<Bean.ResultBean.RxxpBean.CommodityListBean> mjihe;

    public RecyclerHotAdapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Bean.ResultBean.RxxpBean.CommodityListBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(mContext).inflate(R.layout.recycler_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(mjihe.get(i).getMasterPic()).into(viewHolder.Hot_Image);
        viewHolder.Hot_Title.setText(mjihe.get(i).getCommodityName());
        viewHolder.Hot_Price.setText("¥"+mjihe.get(i).getPrice()+"0");
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
                ImageView Hot_Image;
                TextView Hot_Title,Hot_Price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Hot_Image=itemView.findViewById(R.id.Hot_Image);
            Hot_Title=itemView.findViewById(R.id.Hot_Title);
            Hot_Price=itemView.findViewById(R.id.Hot_Price);

        }
    }
}
