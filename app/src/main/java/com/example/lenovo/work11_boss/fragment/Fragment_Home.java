package com.example.lenovo.work11_boss.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.lenovo.work11_boss.R;
import com.example.lenovo.work11_boss.Until.APis;
import com.example.lenovo.work11_boss.adapter.Home_Menu_One_Adapter;
import com.example.lenovo.work11_boss.adapter.Home_Menu_Two_Adapter;
import com.example.lenovo.work11_boss.adapter.Home_Shop_List_Adapter;
import com.example.lenovo.work11_boss.adapter.RecyclerFationAdapter;
import com.example.lenovo.work11_boss.adapter.RecyclerHotAdapter;
import com.example.lenovo.work11_boss.adapter.RecyclerLiftAdapter;
import com.example.lenovo.work11_boss.bean.Bean;
import com.example.lenovo.work11_boss.bean.Home_Menu_One_Bean;
import com.example.lenovo.work11_boss.bean.Home_Menu_Two_Bean;
import com.example.lenovo.work11_boss.bean.Home_Shop_List_Bean;
import com.example.lenovo.work11_boss.bean.XbannerBean;
import com.example.lenovo.work11_boss.iprisenter.IPrenserterImp;
import com.example.lenovo.work11_boss.iview.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment_Home extends Fragment implements IView {
    private View view;
    @BindView(R.id.Home_Menu)
    public ImageView mHomeMenu;
    @BindView(R.id.Hone_Edit)
    public EditText mHomeEdit;
    @BindView(R.id.Home_Search)
    public ImageView mHomeSearch;
    private RecyclerView mRecyclerHot;
    private RecyclerView mRecyclerFation;
    private RecyclerHotAdapter mHotAdapter;
    private List<Bean.ResultBean.RxxpBean.CommodityListBean> mCommodityList;
    private RecyclerFationAdapter mFationAdapter;
    private List<Bean.ResultBean.MlssBean.CommodityListBeanXX> mCommodityList1;
    IPrenserterImp mIPrenserterImp;
    private List<XbannerBean.ResultBean> mResult;
    private RecyclerView mRecyclerLift;
    private RecyclerLiftAdapter mLiftAdapter;
    //条目类
    @BindView(R.id.OnClickMenu)
    public RecyclerView OnClickMenu;
    private Home_Menu_One_Adapter mMenu_one_adapter;
    private Home_Menu_Two_Adapter mMenuTwoAdapter;
    private Home_Shop_List_Adapter mShopListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        //绑定
        ButterKnife.bind(this,view);
        mHomeMenu.bringToFront();
        //轮播图
       initViewPager(view);

        //热门
        initHotView(view);
        //时尚
        initFationView(view);
        //生活
        initLeft(view);
        //二级分类
        TwoMenu();
        return view;
    }

    /**
     * TODO:1.轮播图
     */
    private XBanner mXBanner;
    private void initViewPager(View view) {
            //TODO:qqqqqqqqqq
        mIPrenserterImp.startGet(APis.HOME_RECYCLER_BANNER,XbannerBean.class);
    }

    /**
     * TODO:2.热门
     */
    private void initHotView(View view) {
            mRecyclerHot=view.findViewById(R.id.Home_Hot_Recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        mRecyclerHot.setLayoutManager(manager);
        //适配器
        mHotAdapter = new RecyclerHotAdapter(getActivity());
        mRecyclerHot.setAdapter(mHotAdapter);
        mIPrenserterImp.startGet(APis.HOME_HOT,Bean.class);

    }

    /**
     * TODO：3.时尚
     */
    private void initFationView(View view) {
        mRecyclerFation = view.findViewById(R.id.Home_Fation_Recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerFation.setLayoutManager(manager);
        //适配器
        mFationAdapter = new RecyclerFationAdapter(getActivity());
        mRecyclerFation.setAdapter(mFationAdapter);

    }

    /**
     * TODO:4.品质生活
     */
    private void initLeft(View view) {
        mRecyclerLift = view.findViewById(R.id.Home_Lift_Recycler);
        //管理布局格式
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerLift.setLayoutManager(manager);
        //适配器
        mLiftAdapter = new RecyclerLiftAdapter(getActivity());
        mRecyclerLift.setAdapter(mLiftAdapter);
    }

    /**
     *TODO:5.类目页点击事件
     */

    Boolean boo=false;
    @OnClick(R.id.Home_Menu)
    public void setMenu(){
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        OnClickMenu.setLayoutManager(manager);
        //适配器
        mMenu_one_adapter = new Home_Menu_One_Adapter(getActivity());
        OnClickMenu.setAdapter(mMenu_one_adapter);
        if (boo){
            //隐藏
            OnClickMenu.setVisibility(View.INVISIBLE);
            TwoMenuRecycler.setVisibility(View.INVISIBLE);
        }else {
            //显示
            OnClickMenu.setVisibility(View.VISIBLE);
            TwoMenuRecycler.setVisibility(View.VISIBLE);
        }

        //网络请求
        mIPrenserterImp.startGet(APis.HOME_MENU,Home_Menu_One_Bean.class);

    }

    /**
     * TODO：搜索的点击事件
     */
    @OnClick(R.id.Home_Search)
    public void Homesearch(){
        //将搜索栏展示出来
        mHomeEdit.setVisibility(View.VISIBLE);
        //得到输入的值
        String inputHomeEdit = mHomeEdit.getText().toString();
        //TODO:网络请求------没写
       // mIPrenserterImp.startGet(APis.HOME_KEYWORD+"?keyword="+inputHomeEdit+"&page="+mPage+"&count="+mCount,);
    }



    /**
     * TODO:获取资源ID
     * @param view
     */
    private void initView(View view) {
        mHomeMenu = (ImageView) view.findViewById(R.id.Home_Menu);
        mHomeSearch = (ImageView) view.findViewById(R.id.Home_Search);
        mXBanner = (XBanner) view.findViewById(R.id.xbanner);
        //TODO：qqqqqqqq
        mIPrenserterImp=new IPrenserterImp(this);

    }

    /**
     * TODO：得到数据
     * @param o
     */
    @Override
    public void setData(Object o) {
        if (o instanceof Bean){

            Bean bean=(Bean)o;
            //热门
            List<Bean.ResultBean.RxxpBean> rxxp = bean.getResult().getRxxp();
            for (int i = 0; i <rxxp.size() ; i++) {
                mCommodityList = rxxp.get(i).getCommodityList();
                mHotAdapter.setMjihe(mCommodityList);
            }
            //时尚
            List<Bean.ResultBean.MlssBean> mlss = bean.getResult().getMlss();
            for (int i = 0; i <mlss.size() ; i++) {
                mCommodityList1 = mlss.get(i).getCommodityList();
                mFationAdapter.setMjihe(mCommodityList1);
            }
            //生活
            List<Bean.ResultBean.PzshBean> pzsh = bean.getResult().getPzsh();
            for (int i = 0; i <pzsh.size() ; i++) {
                List<Bean.ResultBean.PzshBean.CommodityListBeanX> beanXList = pzsh.get(i).getCommodityList();
                mLiftAdapter.setMjihe(beanXList);

            }
        }
            //XBanner
        if (o instanceof XbannerBean){
            XbannerBean xbannerBean=(XbannerBean)o;
            mResult = xbannerBean.getResult();
           initXbanner();
        }
        //一级分类
        if (o instanceof Home_Menu_One_Bean){
            final Home_Menu_One_Bean menuOneBean=(Home_Menu_One_Bean)o;
            mMenu_one_adapter.setMjihe(menuOneBean.getResult());
            mMenu_one_adapter.setCallBack(new Home_Menu_One_Adapter.CallBack() {
               @Override
               public void getItem(int i) {
                        //得到点击的一级条目的id
                        initTwoMenu(i);
               }
           });
        }
            //二级分类
        if (o instanceof Home_Menu_Two_Bean){
            Home_Menu_Two_Bean menuTwoBean=(Home_Menu_Two_Bean)o;
            mMenuTwoAdapter.setMjihe(menuTwoBean.getResult());
            mMenuTwoAdapter.setCallBack(new Home_Menu_Two_Adapter.CallBack() {
                @Override
                public void callBack(int i) {
                    //详细类列商品表
                    initListShop(i);
                }
            });
        }

        //详细分类商品列表
        if (o instanceof Home_Shop_List_Bean){
            Home_Shop_List_Bean homeShopListBean=(Home_Shop_List_Bean)o;
            Log.i("TAG","homeShopListBean="+homeShopListBean);
            if (mPage==1){
                mShopListAdapter.setMjihe(homeShopListBean.getResult());
            }else {
                mShopListAdapter.addMjihe(homeShopListBean.getResult());
                mShopListAdapter.notifyDataSetChanged();
            }
            mPage++;
            Home_Shop_List.loadMoreComplete();
            Home_Shop_List.refreshComplete();
        }

    }

    /**
     * TODO:详细分类商品列表
     */
        @BindView(R.id.Home_Shop_List)
        XRecyclerView Home_Shop_List;
        int mPage;
        @BindView(R.id.xbanner)
        XBanner XBanner;
        @BindView(R.id.Hot_Lift_Fashion)
        RelativeLayout Hot_Lift_Fashion;
       @BindView(R.id.OneTwoMenu)
        RelativeLayout OneTwoMenu;
    private void initListShop(final int i) {
            //XRecycler
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        Home_Shop_List.setLayoutManager(manager);
        //适配器
        mShopListAdapter = new Home_Shop_List_Adapter(getActivity());
        Home_Shop_List.setAdapter(mShopListAdapter);
        //显示并隐藏XBanner、Hot_Lift_Fashion
        Home_Shop_List.setVisibility(View.VISIBLE);
        XBanner.setVisibility(View.INVISIBLE);
        Hot_Lift_Fashion.setVisibility(View.INVISIBLE);


            OneTwoMenu.setVisibility(View.INVISIBLE);

        //设置刷新加载
        Home_Shop_List.setLoadingMoreEnabled(true);
        Home_Shop_List.setPullRefreshEnabled(true);
        Home_Shop_List.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override       //刷新
            public void onRefresh() {
                    mPage=1;
                    //请求网络
                   initXRecycler(i,mPage);
                Home_Shop_List.refreshComplete();
            }

            @Override       //加载
            public void onLoadMore() {
                //请求网络
                initXRecycler(i,mPage);
            }
        });
        initXRecycler(i,mPage);

    }

    /**
     * TODO：商品列表的网络请求
     */
        int mCount=5;
    private void initXRecycler(int i,int mpage) {
        mIPrenserterImp.startGet(APis.HOME_SHOP_LIST+"categoryId="+i+"&page="+1+"&count="+mCount,Home_Shop_List_Bean.class);
    }

    /**
     * TODO:点击后的二级分类
     */
    @BindView(R.id.TwoMenuRecycler)
    RecyclerView TwoMenuRecycler;
    private void initTwoMenu(int i) {

        //布局管理
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        TwoMenuRecycler.setLayoutManager(manager);
        //适配器
        mMenuTwoAdapter = new Home_Menu_Two_Adapter(getActivity());
        TwoMenuRecycler.setAdapter(mMenuTwoAdapter);
        //显示
        TwoMenuRecycler.setVisibility(View.VISIBLE);
        //请求网络
        mIPrenserterImp.startGet(APis.HOME_MENU_TWO+i,Home_Menu_Two_Bean.class);
    }

    /**
     * TODO:初始的二级分类
     */
    private void TwoMenu() {
        //布局管理
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        TwoMenuRecycler.setLayoutManager(manager);
        //适配器
        mMenuTwoAdapter = new Home_Menu_Two_Adapter(getActivity());
        TwoMenuRecycler.setAdapter(mMenuTwoAdapter);
        //显示
       // TwoMenuRecycler.setVisibility(View.VISIBLE);
        //请求网络
        int Initial_Value=1001002;
        mIPrenserterImp.startGet(APis.HOME_MENU_TWO+Initial_Value,Home_Menu_Two_Bean.class);
    }




    /**
     * TODO:XBanner展示
     */
    List<String>mXbanner_list;
    private void initXbanner() {
        mXbanner_list=new ArrayList<>();
        for (int i = 0; i <mResult.size() ; i++) {
            String imageUrl = mResult.get(i).getImageUrl();
           mXbanner_list.add(imageUrl);
        }
        // 为XBanner绑定数据
        //第二个参数为提示文字资源集合
        mXBanner .setData(mXbanner_list,null);
        // XBanner适配数据
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getActivity()).load(mXbanner_list.get(position)).into((ImageView) view);
            }
        });
        // 设置XBanner页面切换的时间，即动画时长
        mXBanner.setPageTransformer(Transformer.Default);//横向移动

        mXBanner.setPageTransformer(Transformer.Alpha); //渐变，效果不明显

        mXBanner.setPageTransformer(Transformer.ZoomFade); // 缩小本页，同时放大另一页

        mXBanner.setPageTransformer(Transformer.ZoomCenter); //本页缩小一点，另一页就放大

        mXBanner.setPageTransformer(Transformer.ZoomStack); // 本页和下页同事缩小和放大

        mXBanner.setPageTransformer(Transformer.Stack);  //本页和下页同时左移

        mXBanner.setPageTransformer(Transformer.Zoom);  //本页刚左移，下页就在后面
        mXBanner.setPageChangeDuration(0);

    }


    /**
     * TODO:请求错误的返回结果
     * @param error
     */
    @Override
    public void setError(String error) {

    }

    /**
     * 解绑
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mIPrenserterImp.onDelet();
    }
}
