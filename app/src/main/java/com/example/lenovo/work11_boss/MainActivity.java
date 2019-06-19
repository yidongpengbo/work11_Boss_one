package com.example.lenovo.work11_boss;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.work11_boss.Until.APis;
import com.example.lenovo.work11_boss.bean.LoginBean;
import com.example.lenovo.work11_boss.iprisenter.IPrenserterImp;
import com.example.lenovo.work11_boss.iview.IView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView {

    /**
     * 手机号
     */
    @BindView(R.id.Login_Number)
    public EditText mLoginNumber;
    /**
     * 登录密码
     */
    @BindView(R.id.Login_Password)
    public EditText mLoginPassword;
    @BindView(R.id.Login_Eye)
    public ImageView mLoginEye;
    /**
     * 记住密码
     */
    @BindView(R.id.Remeber_Password)
    public CheckBox mRemeberPassword;
    /**
     * 快速注册
     */
    @BindView(R.id.Rapid_registration)
    public TextView mRapidRegistration;
    /**
     * 登录
     */
    @BindView(R.id.Login)
    public Button mLogin;
    IPrenserterImp mIPrenserterImp;
    private SharedPreferences shard;
    private SharedPreferences.Editor editor;
    LoginBean mLoginBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //沉浸式
        initimmersive();
        //ButterKnife
        ButterKnife.bind(this);

        //得到SharedPreferences
        shard=getSharedPreferences("uuu", MODE_PRIVATE);
        //得到editor
        editor=shard.edit();
        //记住密码的状态
        initRemeber();


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

    /**
     * TODO：2.密文明文更改
     */
    // 输入框密码是否是隐藏的，默认为true
    private boolean isHideFirst = true;
    @OnClick(R.id.Login_Eye)
    public void LoginEye(){
        if (isHideFirst == true) {
            mLoginEye.setImageResource(R.mipmap.login_icon_eye_n_hdhpi);
            //密文
            HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
            mLoginPassword.setTransformationMethod(method1);
            isHideFirst = false;
        } else {
            mLoginEye.setImageResource(R.mipmap.login_icon_eye_n_hdhpi);
            //密文
            TransformationMethod method = PasswordTransformationMethod.getInstance();
            mLoginPassword.setTransformationMethod(method);
            isHideFirst = true;
        }
        // 光标的位置
        int index = mLoginPassword.getText().toString().length();
        mLoginPassword.setSelection(index);

    }



    /**
     * TODO：3.选中登录按钮
     */
    @OnClick(R.id.Login)
    public void Login(){
        initNet();

    }

    /**
     * TODO：3.1对输入的数据请求网络
     */
    private void initNet() {
        HashMap<String, String> map = new HashMap<>();
        //得到输入框的值
        String input_Number = mLoginNumber.getText().toString();
        String input_Password = mLoginPassword.getText().toString();
        String REGEX="[1][3458]\\d{9}";
        if (!input_Number.matches(REGEX)){
            Toast.makeText(this,"手机格式不对",Toast.LENGTH_LONG).show();
        }
        if (input_Password.length()<=0){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_LONG).show();
        }
        if (input_Number.matches(REGEX)&&input_Password.length()>0){
            //进行网络请求
            map.put("phone",input_Number);
            map.put("pwd",input_Password);
            //P层
            mIPrenserterImp=new IPrenserterImp(this);
            mIPrenserterImp.startRequest(map,APis.MAIN_LOGIN,LoginBean.class);
        }


    }



    /**
     * TODO：5.得到数据
     * @param o
     */
    @SuppressLint("ApplySharedPref")
    @Override
    public void setData(Object o) {
            if (o instanceof LoginBean){
                LoginBean loginBean=(LoginBean)o;
                mLoginBean=loginBean;
                SharedPreferences spDemo = getSharedPreferences("spDemo", MODE_PRIVATE);
                //将userID、sessionId保存
                spDemo.edit().putString("userId",loginBean.getResults().getUserId())
                        .putString("sessionId",loginBean.getResults().getSessionId())
                        .commit();
                String message = loginBean.getMessage();
                if (message.equals("登录成功")){
                    //TODO：此时可以做跳转操作
                    //判断是否记住按钮--跳转
                        initIntent();

                }else if (message.equals("请输入密码")){
                    Toast.makeText(this,message,Toast.LENGTH_LONG).show();
                }else if (message.equals("请输入手机号")){
                    Toast.makeText(this,message,Toast.LENGTH_LONG).show();
                } else  {
                    Toast.makeText(this,message,Toast.LENGTH_LONG).show();
                    return;
                }
            }
    }

    /**
     * TODO:5.1判断是否勾选自己密码，并跳转
     */
    private void initIntent() {
        //如果记住密码勾选
        if(mRemeberPassword.isChecked()){
            //获得输入框的值
            String name = mLoginNumber.getText().toString();
            String pass = mLoginPassword.getText().toString();
            //将值存入到shard
            editor.putString("name", name);
            editor.putString("pass", pass);
            //存入一个勾选了的状态值
            editor.putBoolean("ji_ischecked", true);
            //提交，此时，输入的值存到shared中，再下次启动时，将值从shared中取出来放到输入框
            //且记住账号为选中状态，
            editor.commit();
        }else{
            editor.clear();
            editor.commit();
        }
        //跳转、传值
        Intent it=new Intent(MainActivity.this,LoginActivity.class);
        it.putExtra("headPic",mLoginBean.getResults().getHeadPic());
        it.putExtra("nickName",mLoginBean.getResults().getNickName());
        //登录凭证
//        it.putExtra("sessionId",mLoginBean.getResults().getSessionId());
//        it.putExtra("userId",mLoginBean.getResults().getUserId());
        it.putExtra("sex",mLoginBean.getResults().getSex());
        startActivity(it);
        finish();
    }

    /**
     * TODO:5.2记住勾选
     */
    private void initRemeber() {
        boolean ji_checked = shard.getBoolean("ji_ischecked", false);
        if(ji_checked){
            //获取到shared的值
            String ji_name = shard.getString("name", null);
            String ji_pass = shard.getString("pass", null);
            //放入到输入框
            mLoginNumber.setText(ji_name);
            mLoginPassword.setText(ji_pass);
            //记住账号选中
            mRemeberPassword.setChecked(true);
        }
    }

    /**
     * TODO：4.请求失败
     * @param error
     */
    @Override
    public void setError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show();
    }


    /**
     * TODO:快速注册
     */
    @OnClick(R.id.Rapid_registration)
    public void regist(){
        //跳转到注册页
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    /**
     * TODO：6.解绑
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO：一定要判断一下
       if (mIPrenserterImp!=null){
           mIPrenserterImp.onDelet();
       }
    }


}
