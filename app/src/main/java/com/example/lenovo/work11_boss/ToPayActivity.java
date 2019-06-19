package com.example.lenovo.work11_boss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.work11_boss.bean.RegistBean;
import com.example.lenovo.work11_boss.iprisenter.IPrenserterImp;
import com.example.lenovo.work11_boss.iview.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ToPayActivity extends AppCompatActivity implements View.OnClickListener ,IView {
    /**
     * 选择支付方式
     */
    private TextView mZhi;
    private CheckBox mPayBalance;
    private CheckBox mPayWei;
    private CheckBox mPayZhi;
    /**
     * 支付余额768元
     */
    private Button mPayButton;
    IPrenserterImp mIPrenserterImp;
    private String mOrderID;
    /**
     * 支付成功、失败页
     */
    RelativeLayout mToPaySuccess,mToPayError;
    /**
     * 返回主页、查看订单、继续支付
     */
    Button mSuccessReturn,mSuccessSee,mErrorPayMent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topay);

        initimmersive();
        initView();
        Intent intent = getIntent();
        mOrderID = intent.getStringExtra("orderID");
        double price = intent.getDoubleExtra("price", 0);
        mPayButton.setText("余额支付"+price+"元");

    }

    private void initView() {
        mZhi = (TextView) findViewById(R.id.zhi);
        mPayBalance = (CheckBox) findViewById(R.id.payBalance);
        mPayBalance.setOnClickListener(this);
        mPayWei = (CheckBox) findViewById(R.id.payWei);
        mPayZhi = (CheckBox) findViewById(R.id.payZhi);
        mPayButton = (Button) findViewById(R.id.payButton);
        mPayButton.setOnClickListener(this);
        mIPrenserterImp=new IPrenserterImp(this);
        mToPaySuccess=findViewById(R.id.topay_success);
        mToPayError=findViewById(R.id.topay_error);
        mSuccessReturn=findViewById(R.id.success_Return);
        mSuccessReturn.setOnClickListener(this);
        mSuccessSee=findViewById(R.id.success_See);
        mSuccessSee.setOnClickListener(this);
        mErrorPayMent=findViewById(R.id.error_payment);
        mErrorPayMent.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.payBalance:
                break;
                //支付
            case R.id.payButton:
                initButton();
                break;
                //返回主页
            case R.id.success_Return:
                List<Float> mlist=new ArrayList<>();
              //  EventBus.getDefault().post(mlist);
                break;
                //查看订单
            case R.id.success_See:
                finish();
                break;
                //继续支付
            case R.id.error_payment:
                mToPayError.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void initButton() {
        if (mPayBalance.isChecked()&&!mPayWei.isChecked()&&!mPayWei.isChecked()) {
            //网络请求
            HashMap<String, String> map = new HashMap<>();
            map.put("orderId", mOrderID);
            map.put("payType", 1 + "");
            String path = "order/verify/v1/pay";
            mIPrenserterImp.startRequest(map, path, RegistBean.class);
        }else {
            //TODO:支付失败
            mToPayError.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 获取数量
     * @param o
     */
    @Override
    public void setData(Object o) {
        if (o instanceof RegistBean){
            RegistBean registBean=(RegistBean)o;
            mToPaySuccess.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void setError(String error) {
        Toast.makeText(this, "错误："+error, Toast.LENGTH_SHORT).show();
    }

    /**
     * TODO：1.沉浸式
     */
    private void initimmersive() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
