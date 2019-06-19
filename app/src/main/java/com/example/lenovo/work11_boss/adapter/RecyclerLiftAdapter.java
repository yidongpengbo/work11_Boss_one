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

public class RecyclerLiftAdapter extends RecyclerView.Adapter<RecyclerLiftAdapter.ViewHolder> {
        private Context mContext;
        private List<Bean.ResultBean.PzshBean.CommodityListBeanX>mjihe;

    public RecyclerLiftAdapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Bean.ResultBean.PzshBean.CommodityListBeanX> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(mContext).inflate(R.layout.recycler_lift_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(mContext).load(mjihe.get(i).getMasterPic()).into(viewHolder.LiftImage);
        viewHolder.LiftTitle.setText(mjihe.get(i).getCommodityName());
        viewHolder.LiftPrice.setText("¥"+mjihe.get(i).getPrice()+"");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到点击的下标
                mCallBack.getData(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView LiftImage;
            TextView LiftTitle,LiftPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            LiftImage=itemView.findViewById(R.id.Lift_Image);
            LiftTitle=itemView.findViewById(R.id.Lift_Title);
            LiftPrice=itemView.findViewById(R.id.Lift_Price);
        }
    }

    public interface CallBack{
        void getData(int i);
    }
    public RecyclerHotAdapter.CallBack mCallBack;

    public void setCallBack(RecyclerHotAdapter.CallBack callBack) {
        mCallBack = callBack;
    }
}
