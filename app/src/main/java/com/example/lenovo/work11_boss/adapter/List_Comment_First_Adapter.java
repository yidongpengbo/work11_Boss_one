package com.example.lenovo.work11_boss.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.work11_boss.R;
import com.example.lenovo.work11_boss.Until.DateUntil;
import com.example.lenovo.work11_boss.bean.EventBusOrderIdBean;
import com.example.lenovo.work11_boss.bean.Fragment_List_Bean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lenovo
 * TODO:待评价的FirstRecycler
 */
public class List_Comment_First_Adapter extends RecyclerView.Adapter<List_Comment_First_Adapter.ViewHolder> {
            private Context mContext;
            private List<Fragment_List_Bean.OrderListBean>mjihe;

    public List_Comment_First_Adapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Fragment_List_Bean.OrderListBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.list_comment_first_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
                //订单号：
            viewHolder.CommentOrderCode.setText(mjihe.get(i).getOrderId()+"");
        String orderId = mjihe.get(i).getOrderId();
        EventBusOrderIdBean bean = new EventBusOrderIdBean();
        bean.setOrderId(orderId);
        EventBus.getDefault().postSticky(bean);
        //时间：
        String dateToString = DateUntil.DateUtils.getDateToString(mjihe.get(i).getOrderTime());
        viewHolder.CommentTime.setText(dateToString);
            //recyycler
        List_Comment_Second_Adapter secondAdapter = new List_Comment_Second_Adapter(mContext, mjihe.get(i).getDetailList());
            viewHolder.CommentSecondRecycler.setAdapter(secondAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.CommentSecondRecycler.setLayoutManager(manager);
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            TextView CommentOrderCode;
            RecyclerView CommentSecondRecycler;
            TextView CommentTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CommentOrderCode=itemView.findViewById(R.id.CommentOrderCode);
            CommentSecondRecycler=itemView.findViewById(R.id.CommentSecondRecycler);
            CommentTime=itemView.findViewById(R.id.CommentTime);


        }
    }
}
