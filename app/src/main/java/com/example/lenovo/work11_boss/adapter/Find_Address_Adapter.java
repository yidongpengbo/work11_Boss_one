package com.example.lenovo.work11_boss.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.work11_boss.R;
import com.example.lenovo.work11_boss.bean.Find_Address_List_Bean;

import java.util.ArrayList;
import java.util.List;

public class Find_Address_Adapter extends RecyclerView.Adapter<Find_Address_Adapter.ViewHolder> {
    private Context mContext;
    private List<Find_Address_List_Bean.ResultBean> mjihe;

    public Find_Address_Adapter(Context context) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.find_address_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
                final String realName = mjihe.get(i).getRealName();
                String phone = mjihe.get(i).getPhone();
                String address = mjihe.get(i).getAddress();
        viewHolder.AddresseeName.setText(realName);
            viewHolder.AddresseePhone.setText(phone);
            viewHolder.AddresseeDetailedAddress.setText(address);
        //TODO:单选框、Button....
            viewHolder.AddresseeDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){

                            mCheckbox.check(mjihe,i);
                        }
                    }

            });
            //修改
            viewHolder.AddresseeUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "修改地址", Toast.LENGTH_SHORT).show();
                }
            });
            //删除
        viewHolder.AddresseeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "从我的地址列表删除地址", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
                //名字            电话                  详细地址
        TextView AddresseeName,AddresseePhone,AddresseeDetailedAddress;
                    //默认
        CheckBox AddresseeDefault;
                //修改             删除
        Button AddresseeUpdate,AddresseeDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            AddresseeName=itemView.findViewById(R.id.AddresseeName);
            AddresseePhone=itemView.findViewById(R.id.AddresseePhone);
            AddresseeDetailedAddress=itemView.findViewById(R.id.AddresseeDetailedAddress);
            AddresseeDefault=itemView.findViewById(R.id.AddresseeDefault);
            AddresseeUpdate=itemView.findViewById(R.id.AddresseeUpdate);
            AddresseeDelete=itemView.findViewById(R.id.AddresseeDelete);
        }

    }

    /**
     * 接口
     */
    public interface Checkbox{
        void check(List<Find_Address_List_Bean.ResultBean> mlist,int i);
    }
    public Checkbox mCheckbox;

    public void setCheckbox(Checkbox checkbox) {
        mCheckbox = checkbox;
    }
}
