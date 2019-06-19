package com.example.lenovo.work11_boss.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.work11_boss.GoCommentActivity;
import com.example.lenovo.work11_boss.R;
import com.example.lenovo.work11_boss.bean.Fragment_List_Bean;

import java.util.List;

public class List_Comment_Second_Adapter extends RecyclerView.Adapter<List_Comment_Second_Adapter.ViewHolder> {
            private Context mContext;
            private List<Fragment_List_Bean.OrderListBean.DetailListBean>mjihe;

    public List_Comment_Second_Adapter(Context context, List<Fragment_List_Bean.OrderListBean.DetailListBean> mjihe) {
        mContext = context;
        this.mjihe = mjihe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.list_comment_second_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
            //图片
        final String image = mjihe.get(i).getCommodityPic().split("\\,")[0];
        Glide.with(mContext).load(image).into(viewHolder.CommentSecondImage);
        //标题
        viewHolder.CommentSecondName.setText(mjihe.get(i).getCommodityName());
        //价钱
        viewHolder.CommentSecondPrice.setText("¥"+mjihe.get(i).getCommodityPrice());
        //TODO：去评论的点击事件
        viewHolder.CommentSecondGoComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoCommentActivity.class);
                //传值
                intent.putExtra("GoImage",image);
                intent.putExtra("GoName",mjihe.get(i).getCommodityName());
                intent.putExtra("GoPrice",mjihe.get(i).getCommodityPrice());
                intent.putExtra("CommodityId",mjihe.get(i).getCommodityId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView CommentSecondImage;
        TextView CommentSecondName,CommentSecondPrice,CommentSecondGoComment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CommentSecondImage=itemView.findViewById(R.id.CommentSecondImage);
            CommentSecondName=itemView.findViewById(R.id.CommentSecondName);
            CommentSecondPrice=itemView.findViewById(R.id.CommentSecondPrice);
            CommentSecondGoComment=itemView.findViewById(R.id.CommentSecondGoComment);
        }
    }
}
