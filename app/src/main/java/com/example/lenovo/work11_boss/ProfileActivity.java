package com.example.lenovo.work11_boss;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProfileActivity extends AppCompatActivity {
    @BindView(R.id.Profile_head_Image)
    public SimpleDraweeView mProfileHeadImage;
    @BindView(R.id.Profile_Name)
    public TextView mProfileName;
    @BindView(R.id.Profile_Password)
    public TextView mProfilePassword;
    private PopupWindow mPopupWindow;
    private TextView mModify;
    private TextView mPhotograph;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
       //赋值
        initVelue();
        //设置popupwindow
        initPopup();
        //从相册修改
        initModify();

    }

    /**
     * 获取值
     */
    private void initVelue() {
        //得到传的值
        Intent intent = getIntent();
        String profit_headPic = intent.getStringExtra("Profit_headPic");
        String profit_nickName = intent.getStringExtra("Profit_nickName");
        String profit_password = intent.getStringExtra("Profit_password");
        //赋值
        Uri parse = Uri.parse(profit_headPic);
        mProfileHeadImage.setImageURI(parse);
        mProfileName.setText(profit_nickName);
        mProfilePassword.setText(profit_password);
    }

    /**
     * 设置popupwindow
     */
    private void initPopup() {
        //加载popupWindow的子布局
        View view = View.inflate(this, R.layout.profileactivity_head_popupwindow, null);
        //获取popupWindow中的控件
        //通过子布局中的到ID
        mModify = (TextView) view.findViewById(R.id.Modify_Head);
        mPhotograph = (TextView) view.findViewById(R.id.Photograph);
        //1.创建popupwindow   contentView 子布局  width,宽   height 高
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置焦点
        mPopupWindow.setFocusable(true);
        //设置背景  ColorDrawable(int) 颜色背景
        //1.new ColorDrawable(R.color.bai) 在values下创建color.xml
        //2.new ColorDrawable(Color.WHITE)
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.RED));
        //设置是否可以触摸
        mPopupWindow.setTouchable(true);
    }

    /**
     * 点击头像，弹出pubwindow
     */
    @OnClick(R.id.Profile_head_Image)
    public void headImage(){
       mPopupWindow.showAsDropDown(mProfileHeadImage);

    }

    /**
     * 从相册修改
     */
    public void initModify(){
        mModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpick(v);
                    mPopupWindow.dismiss();
            }
        });
    }

    //打开相册
    public void openpick(View v){
        Intent intent = new Intent(Intent.ACTION_PICK);
        //设置图片的格式
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //调取裁剪
        if(requestCode == 100 && resultCode == RESULT_OK){
            //得到相册图片的路径
            Uri uri = data.getData();
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(uri, "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出的大小
            intent.putExtra("outputX", 250);
            intent.putExtra("outputY", 250);
            //将图片进行返回
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 200);

        }

        if(requestCode == 200 && resultCode == RESULT_OK){
            Bitmap bitmap = data.getParcelableExtra("data");
            mProfileHeadImage.setImageBitmap(bitmap);
            //TODO:将图片保存到......

        }


    }

}
