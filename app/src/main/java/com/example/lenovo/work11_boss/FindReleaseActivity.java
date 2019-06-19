package com.example.lenovo.work11_boss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindReleaseActivity extends AppCompatActivity {
    @BindView(R.id.Back)
     Button mBack;
    /**
     * 发布
     */
    @BindView(R.id.release)
     Button mRelease;
    /**
     * 描述
     */
    @BindView(R.id.Describe)
     EditText mDescribe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findrelease);
        ButterKnife.bind(this);
    }

    /**
     * 返回的点击事件
     */
    @OnClick(R.id.Back)
    public void setBack(){
            finish();
    }

    /**
     * 发布的点击事件
     */
    @OnClick(R.id.release)
    public void setRelease(){
        //获取到描述的内容，
        String inputDescribe = mDescribe.getText().toString();

    }





}
