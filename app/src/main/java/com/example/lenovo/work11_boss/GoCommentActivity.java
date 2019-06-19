package com.example.lenovo.work11_boss;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.work11_boss.Until.APis;
import com.example.lenovo.work11_boss.bean.EventBusOrderIdBean;
import com.example.lenovo.work11_boss.bean.RegistBean;
import com.example.lenovo.work11_boss.iprisenter.IPrenserterImp;
import com.example.lenovo.work11_boss.iview.IView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

/**
 * @author lenovo
 *      评价
 */
public class GoCommentActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private ImageView mGoImage;
    private TextView mGoName;
    private TextView mGoPrice;
    private Button mGoComment;
    private EditText CommentEdit;
    private ImageView CommentImage;
    private String filepath = Environment.getExternalStorageDirectory()
            + "/file.png";
    IPrenserterImp mIPrenserterImp;
    private String mOrderId;
    private int mCommodityId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gocomment);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        initimmersive();
        initView();
        Intent intent = getIntent();
        String goImage = intent.getStringExtra("GoImage");
        String goName = intent.getStringExtra("GoName");
        int goPrice = intent.getIntExtra("GoPrice", 50);
        mCommodityId = intent.getIntExtra("CommodityId", 0);
        Glide.with(this).load(goImage).into(mGoImage);
        mGoName.setText(goName);
        mGoPrice.setText("¥"+goPrice);
    }

    private void initView() {
        mGoImage = (ImageView) findViewById(R.id.GoImage);
        mGoName = (TextView) findViewById(R.id.GoName);
        mGoPrice = (TextView) findViewById(R.id.GoPrice);
        mGoComment=(Button)findViewById(R.id.GoComment);
        mGoComment.setOnClickListener(this);
        CommentEdit=(EditText)findViewById(R.id.CommentEdit);
        CommentImage=(ImageView)findViewById(R.id.CommentImage);
        CommentImage.setOnClickListener(this);
        mIPrenserterImp=new IPrenserterImp(this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.GoComment:
                    initGoComment();
                    break;
                case R.id.CommentImage:
                    openpick();
                 default:
                     break;
            }
    }

    //打开相册
    public void openpick(){
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
        if (requestCode == 100 && resultCode == RESULT_OK) {
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

        if (requestCode == 200 && resultCode == RESULT_OK) {
            Bitmap bitmap = data.getParcelableExtra("data");
            ImageFileUntil.setBitmap(bitmap, filepath, 50);
            //TODO:网络请求
            HashMap<String, String> map = new HashMap<>();
            map.put("image", filepath);
            Log.i("TAG", "file:");
          //  mIPrenserterImp.startImage(APis.IMAGE_FILE, map, ImageFileBean.class);

        }
    }
    /**
     * 发表的点击事件
     */
    private void initGoComment(){
        //得到评价的数据
        String inputEdit = CommentEdit.getText().toString();
        //商品ID，订单ID，
        HashMap<String, String> map = new HashMap<>();
        map.put("commodityId",mCommodityId+"");
        map.put("orderId",mOrderId);
        map.put("content",inputEdit);
        map.put("image",filepath);
        mIPrenserterImp.startImage(APis.LIST_COMMENT_GO,map,RegistBean.class);
    }

    /**
     * 得到订单iD
     * @param orderID
     */
    @Subscribe(sticky = true)
    public void toData(EventBusOrderIdBean orderID){
    mOrderId = orderID.getOrderId();
}
    /**
     * 得到数据
     * @param o
     */
    @Override
    public void setData(Object o) {
        if (o instanceof RegistBean){
            RegistBean registBean=(RegistBean)o;
            Toast.makeText(this, registBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取失败
     * @param error
     */
    @Override
    public void setError(String error) {
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }
}
