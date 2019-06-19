package com.example.lenovo.work11_boss.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.work11_boss.R;
import com.example.lenovo.work11_boss.bean.Home_Menu_One_Bean;

import java.util.ArrayList;
import java.util.List;

public class Home_Menu_One_Adapter extends RecyclerView.Adapter<Home_Menu_One_Adapter.ViewHolder> {
        private Context mContext;
        private List<Home_Menu_One_Bean.ResultBean>mjihe;
        private int onclick=0;
    public Home_Menu_One_Adapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Home_Menu_One_Bean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(mContext).inflate(R.layout.home_menu_first_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
                viewHolder.One_Meun.setText(mjihe.get(i).getName());
               final int id = mjihe.get(i).getId();
        //条目点击事件
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCallBack!=null){
                            mCallBack.getItem(id);
                            //刷新
                            onclick=i;
                            notifyDataSetChanged();
                        }
                    }
                });
                //TODO:改变点击的item的颜色
                if (onclick==i){
                    viewHolder.One_Meun.setTextColor(Color.RED);
                }else {
                    viewHolder.One_Meun.setTextColor(Color.WHITE);
                }
    }


    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
                TextView One_Meun;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            One_Meun=itemView.findViewById(R.id.One_Menu);
        }
    }

    /**
     * 接口
     */
    public interface CallBack{
        void getItem(int i);
    }
    private CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }
}
