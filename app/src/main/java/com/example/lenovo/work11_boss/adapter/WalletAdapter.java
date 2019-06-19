package com.example.lenovo.work11_boss.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.work11_boss.R;
import com.example.lenovo.work11_boss.Until.DateUntil;
import com.example.lenovo.work11_boss.bean.Find_Wallet_Bean;

import java.util.ArrayList;
import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {
        private Context mContext;
        private List<Find_Wallet_Bean.ResultBean.DetailListBean>mjihe;

    public WalletAdapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Find_Wallet_Bean.ResultBean.DetailListBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(mContext).inflate(R.layout.walletadapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.walletPrice.setText("¥"+mjihe.get(i).getAmount());
        String dateToString = DateUntil.DateUtils.getDateToString(mjihe.get(i).getCreateTime());
        viewHolder.walletTime.setText(dateToString);
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView walletPrice,walletTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            walletPrice=itemView.findViewById(R.id.walletPrice);
            walletTime=itemView.findViewById(R.id.walletTime);
        }
    }
}
