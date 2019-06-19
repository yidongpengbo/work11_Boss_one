package com.example.lenovo.work11_boss.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.work11_boss.FindActivity;
import com.example.lenovo.work11_boss.FindAddressActivity;
import com.example.lenovo.work11_boss.FoodActivity;
import com.example.lenovo.work11_boss.ProfileActivity;
import com.example.lenovo.work11_boss.R;
import com.example.lenovo.work11_boss.Until.APis;
import com.example.lenovo.work11_boss.WalletActivity;
import com.example.lenovo.work11_boss.bean.Main_Profile_Bean;
import com.example.lenovo.work11_boss.iprisenter.IPrenserterImp;
import com.example.lenovo.work11_boss.iview.IView;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment_Main extends Fragment implements IView {
    private View view;
    @BindView(R.id.Main_Name)
    public TextView mMainName;
    /**
     * 个人资料
     */
    @BindView(R.id.Main_Profile_Text)
    public TextView mMainProfileText;
    /**
     * 我的圈子
     */
    @BindView(R.id.Main_Find_Text)
    public TextView mMainFindText;
    /**
     * 我的足迹
     */
    @BindView(R.id.Main_Foot_Text)
    public TextView mMainFootText;
    /**
     * 我的钱包
     */
    @BindView(R.id.Main_Wallet_Text)
    public TextView mMainWalletText;
    /**
     * 我的收货地址
     */
    @BindView(R.id.Main_Address_Text)
    public TextView mMainAddressText;
    /**
     * 我的头像
     */
    @BindView(R.id.Main_Head_Image)
    public SimpleDraweeView mMainHeadImage;
    IPrenserterImp mIPrenserterImp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,view);
        mIPrenserterImp=new IPrenserterImp(this);
        //获取头像和名字
        initHead_Name();

        return view;
    }

    /**
     * TODO:个人资料点击事件
     */
    @OnClick(R.id.Main_Profile_Text)
    public void initProfile() {
        //网络请求
        mIPrenserterImp.startGet(APis.MAIN_PROFILE,Main_Profile_Bean.class);


    }

    /**
     * TODO：获取登录成功后的头像和名字
     */
    private void initHead_Name() {
        Intent intent = getActivity().getIntent();
        String headPic = intent.getStringExtra("headPic");
        String nickName = intent.getStringExtra("nickName");
        Uri parse = Uri.parse(headPic);
        mMainHeadImage.setImageURI(parse);
        mMainName.setText(nickName);
    }

    /**
     * TODO:我的圈子
     */
    @OnClick(R.id.Main_Find_Text)
    public void initFind(){
        Intent intent = new Intent(getActivity(), FindActivity.class);
        startActivity(intent);
    }

    /**
     * TODO:我的钱包
     */
    @OnClick(R.id.Main_Wallet_Text)
    public void initWallet(){
        Intent intent = new Intent(getActivity(), WalletActivity.class);
        startActivity(intent);
    }

    /**
     * TODO:我的地址
     */
    @OnClick(R.id.Main_Address_Text)
    public void initAddress(){
            //跳转到Activity
        Intent intent = new Intent(getActivity(), FindAddressActivity.class);
        startActivity(intent);
    }

    /**
     * TODO:我的足迹
     */
    @OnClick(R.id.Main_Foot_Text)
    public void initFoot() {
        Intent intent = new Intent(getActivity(), FoodActivity.class);
        startActivity(intent);
    }

    /**
     * 获得数据
     * @param o
     */
    @Override
    public void setData(Object o) {
            if (o instanceof Main_Profile_Bean){
                Main_Profile_Bean mainProfileBean=(Main_Profile_Bean)o;
                //获取到头像，昵称，密码传值
                Main_Profile_Bean.ResultBean profileBeanResult = mainProfileBean.getResult();

                    String headPic = profileBeanResult.getHeadPic();
                    String nickName = profileBeanResult.getNickName();
                    String password = profileBeanResult.getPassword();
                    //传值
                    Intent intentProfit = new Intent(getActivity(), ProfileActivity.class);
                    Intent profit_headPic = intentProfit.putExtra("Profit_headPic",headPic );
                    Intent profit_nickName = intentProfit.putExtra("Profit_nickName", nickName);
                    Intent profit_password = intentProfit.putExtra("Profit_password", password);
                    //跳转到个人信息
                    getActivity().startActivity(intentProfit);


            }

    }

    /**
     * 数据获取失败
     * @param error
     */
    @Override
    public void setError(String error) {
        Toast.makeText(getActivity(), "error="+error, Toast.LENGTH_SHORT).show();
    }
}
