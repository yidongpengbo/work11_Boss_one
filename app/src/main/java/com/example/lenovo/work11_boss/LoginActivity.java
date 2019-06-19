package com.example.lenovo.work11_boss;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lenovo.work11_boss.fragment.Fragment_Find;
import com.example.lenovo.work11_boss.fragment.Fragment_Home;
import com.example.lenovo.work11_boss.fragment.Fragment_List;
import com.example.lenovo.work11_boss.fragment.Fragment_Main;
import com.example.lenovo.work11_boss.fragment.Fragment_Shop_Car;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//import com.example.lenovo.work11_boss.fragment.Fragment_Shop_Car;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.Login_View)
    ViewPager mViewPager;
    private RadioButton mRadioHome;
    private RadioButton mRadioFind;
    @BindView(R.id.Radio_Shop_Car)
     RadioButton mRadioShopCar;
    private RadioButton mRadioList;
    private RadioButton mRadioMain;
    @BindView(R.id.Radio_Group)
    public RadioGroup mRadioGroup;
    private List<Fragment> mjihe;
    Unbinder unbinder;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
        initViewPager();
        initRadio();
        //沉浸式
        initimmersive();
//        if (!EventBus.getDefault().isRegistered(this)){
//            EventBus.getDefault().register(this);
//        }
        }



    /**
     * TODO:添加fragment、适配器
     */
    private void initViewPager() {
        mjihe=new ArrayList<>();
        //添加fragment
        mjihe.add(new Fragment_Home());
        mjihe.add(new Fragment_Find());
        mjihe.add(new Fragment_Shop_Car());
        mjihe.add(new Fragment_List());
        mjihe.add(new Fragment_Main());
        //适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mjihe.get(i);
            }

            @Override
            public int getCount() {
                return mjihe.size();
            }
        });
    }

    /**
     * TODO:切换按钮，fragmnt改变
     */
    private void initRadio() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Radio_Home:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.Radio_Find:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.Radio_Shop_Car:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.Radio_List:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.Radio_Main:
                        mViewPager.setCurrentItem(4);
                        break;
                     default:
                         break;
                }
            }
        });
    }

    private void initimmersive() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    //（注意要接受的值的类型）
//    public void onTouch(List<Float> event){
//            mViewPager.setCurrentItem(0);
//    }

    @Override
    public void onStop() {
        super.onStop();
        //当停止时，取消订阅
        EventBus.getDefault().unregister(this);
    }
}
