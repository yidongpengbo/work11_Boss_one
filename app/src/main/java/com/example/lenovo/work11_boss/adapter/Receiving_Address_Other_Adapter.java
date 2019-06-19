package com.example.lenovo.work11_boss.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.work11_boss.R;
import com.example.lenovo.work11_boss.bean.Find_Address_List_Bean;

import java.util.ArrayList;
import java.util.List;

public class Receiving_Address_Other_Adapter extends RecyclerView.Adapter<Receiving_Address_Other_Adapter.ViewHolder> {
    private Context mContext;
    private List<Find_Address_List_Bean.ResultBean> mjihe;

    public Receiving_Address_Other_Adapter(Context context) {
        mContext = context;
        mjihe = new ArrayList<>();
    }
    public void setMjihe(List<Find_Address_List_Bean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.receiving_address_other_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.OtherName.setText(mjihe.get(i).getRealName());
        viewHolder.OtherPhone.setText(mjihe.get(i).getPhone());
        viewHolder.OtherAddress.setText(mjihe.get(i).getAddress());
        //TODO：选择的点击事件
        viewHolder.OtherChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到点击的条目
                mCallBack.call(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView OtherName,OtherChose,OtherPhone,OtherAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            OtherName=itemView.findViewById(R.id.OtherName);
            OtherChose=itemView.findViewById(R.id.OtherChose);
            OtherPhone=itemView.findViewById(R.id.OtherPhone);
            OtherAddress=itemView.findViewById(R.id.OtherAddress);
        }
    }
    public interface CallBack{
        void call(int i);
    }
    CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }
}
