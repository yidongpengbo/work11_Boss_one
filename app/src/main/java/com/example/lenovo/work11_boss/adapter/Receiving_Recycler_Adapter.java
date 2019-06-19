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
import com.example.lenovo.work11_boss.bean.Fragment_Shop_Car_Bean;

import java.util.List;

public class Receiving_Recycler_Adapter extends RecyclerView.Adapter<Receiving_Recycler_Adapter.ViewHolder> {
        private Context mContext;
        private List<Fragment_Shop_Car_Bean.ResultBean>  mjihe;

    public Receiving_Recycler_Adapter(Context context, List<Fragment_Shop_Car_Bean.ResultBean> mjihe) {
        mContext = context;
        this.mjihe = mjihe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(mContext).inflate(R.layout.activity_receiving_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(mjihe.get(i).getPic()).into(viewHolder.Receiving_Adapter_Image);
        viewHolder.Receiving_Adapter_Name.setText(mjihe.get(i).getCommodityName());
        viewHolder.Receiving_Adapter_Price.setText("¥"+mjihe.get(i).getPrice()+"0");

    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView Receiving_Adapter_Image;
            TextView Receiving_Adapter_Name,Receiving_Adapter_Price;
             Shop_Car_Add_Reduce_View Receiving_Adapter_View;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Receiving_Adapter_Image=itemView.findViewById(R.id.Receiving_Adapter_Image);
            Receiving_Adapter_Name=itemView.findViewById(R.id.Receiving_Adapter_Name);
            Receiving_Adapter_Price=itemView.findViewById(R.id.Receiving_Adapter_Price);
            Receiving_Adapter_View=itemView.findViewById(R.id.Receiving_Adapter_View);
        }
    }
}
