package com.example.lenovo.work11_boss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.work11_boss.Until.APis;
import com.example.lenovo.work11_boss.bean.RegistBean;
import com.example.lenovo.work11_boss.iprisenter.IPrenserterImp;
import com.example.lenovo.work11_boss.iview.IView;

import java.util.HashMap;

public class ReceiveOkActivity extends AppCompatActivity implements IView {
    IPrenserterImp mIPrenserterImp;
        TextView mTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiveok);
        mIPrenserterImp=new IPrenserterImp(this);
        mTextView=findViewById(R.id.OKText);
        Intent intent = getIntent();
        String orderid = intent.getStringExtra("ORDERID");
        HashMap<String, String> map = new HashMap<>();
        map.put("orderId",orderid);
        Log.i("TAG",orderid);
        mIPrenserterImp.startPut(map,APis.LIST_SHOU,RegistBean.class);
        mTextView.setText("确认收货成功");
    }

    @Override
    public void setData(Object o) {
                //确认收货
                if (o instanceof RegistBean){
                    RegistBean registBean=(RegistBean)o;
                    Log.i("TAG",registBean+"");
                    Log.i("TAG",registBean.getMessage());

                    //mTextView.setText(registBean.getMessage());
                   finish();
                }

    }

    @Override
    public void setError(String error) {
        Toast.makeText(this, "错误："+error, Toast.LENGTH_SHORT).show();
    }
}
