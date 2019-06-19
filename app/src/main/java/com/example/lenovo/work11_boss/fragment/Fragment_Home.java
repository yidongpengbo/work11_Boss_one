package com.example.lenovo.work11_boss.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.work11_boss.R;
import com.example.lenovo.work11_boss.Until.APis;
import com.example.lenovo.work11_boss.adapter.Home_Menu_One_Adapter;
import com.example.lenovo.work11_boss.adapter.Home_Menu_Two_Adapter;
import com.example.lenovo.work11_boss.adapter.Home_Shop_Details_Recycler_Adapter;
import com.example.lenovo.work11_boss.adapter.Home_Shop_List_Adapter;
import com.example.lenovo.work11_boss.adapter.RecyclerFationAdapter;
import com.example.lenovo.work11_boss.adapter.RecyclerHotAdapter;
import com.example.lenovo.work11_boss.adapter.RecyclerLiftAdapter;
import com.example.lenovo.work11_boss.bean.Bean;
import com.example.lenovo.work11_boss.bean.Fragment_Shop_Car_Bean;
import com.example.lenovo.work11_boss.bean.Home_Menu_One_Bean;
import com.example.lenovo.work11_boss.bean.Home_Menu_Two_Bean;
import com.example.lenovo.work11_boss.bean.Home_Shop_Add_Car;
import com.example.lenovo.work11_boss.bean.Home_Shop_Comment_List_Bean;
import com.example.lenovo.work11_boss.bean.Home_Shop_Details_List_Bean;
import com.example.lenovo.work11_boss.bean.Home_Shop_List_Bean;
import com.example.lenovo.work11_boss.bean.Query_Shop_Car;
import com.example.lenovo.work11_boss.bean.RegistBean;
import com.example.lenovo.work11_boss.bean.XbannerBean;
import com.example.lenovo.work11_boss.iprisenter.IPrenserterImp;
import com.example.lenovo.work11_boss.iview.IView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
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
    @BindView(R.id.Home_Search_Error_Image)
    public ImageView Home_Search_Error_Image;
    @BindView(R.id.Home_Search_Error_Text)
    public TextView Home_Search_Error_Text;
    private Home_Shop_Details_Recycler_Adapter mHomeShopDetailsRecyclerAdapter;
    //商品详情：
    @BindView(R.id.Home_Shop_Details_Image)
    ImageView Home_Shop_Details_Image;
        //添加到购物车
    @BindView(R.id.Shop_Add_Car)
   ImageView Shop_Add_Car;
    private ImageView mShopAddCar;
    private int mCommodityId;
    private List<Home_Shop_Add_Car> mHomeShopAddCars;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        //绑定
        ButterKnife.bind(this, view);
        mHomeMenu.bringToFront();
        //轮播图
        //时尚
        initViewPager(view);

        //热门
        initHotView(view);
        initFationView(view);
        //生活
        initLeft(view);
        //二级分类
        TwoMenu();
        //商品详情recyclerview
        shopDetails();
        //TODO:添加到购物车的点击事件!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        mShopAddCar = (ImageView) view.findViewById(R.id.Shop_Add_Car);
        mShopAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //判断购物车里是否有数据
            mIPrenserterImp.startGet(APis.FRAGEMENT_SHOP_CAR,Fragment_Shop_Car_Bean.class);

            }
        });
        return view;
    }



    /**
     * TODO:1.轮播图
     */
    private XBanner mXBanner;

    private void initViewPager(View view) {
        //TODO:qqqqqqqqqq
        mIPrenserterImp.startGet(APis.HOME_RECYCLER_BANNER, XbannerBean.class);
    }

    /**
     * TODO:2.热门
     */
    private void initHotView(View view) {
        mRecyclerHot = view.findViewById(R.id.Home_Hot_Recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        mRecyclerHot.setLayoutManager(manager);
        //适配器
        mHotAdapter = new RecyclerHotAdapter(getActivity());
        mRecyclerHot.setAdapter(mHotAdapter);
        mIPrenserterImp.startGet(APis.HOME_HOT, Bean.class);

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
     * TODO:5.类目页点击事件
     */

    Boolean boo = false;

    @OnClick(R.id.Home_Menu)
    public void setMenu() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        OnClickMenu.setLayoutManager(manager);
        //适配器
        mMenu_one_adapter = new Home_Menu_One_Adapter(getActivity());
        OnClickMenu.setAdapter(mMenu_one_adapter);
        if (boo) {
            //隐藏
            OnClickMenu.setVisibility(View.INVISIBLE);
            TwoMenuRecycler.setVisibility(View.INVISIBLE);
        } else {
            //显示
            OnClickMenu.setVisibility(View.VISIBLE);
            TwoMenuRecycler.setVisibility(View.VISIBLE);
        }

        //网络请求
        mIPrenserterImp.startGet(APis.HOME_MENU, Home_Menu_One_Bean.class);

    }

    /**
     * TODO：搜索的点击事件-----与商品列表展示公用同一个XRecycler
     */
    @OnClick(R.id.Home_Search)
    public void HomeSearch() {
        //TODO:网络请求
        initShopListXRecycler();
        Home_Shop_List.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                initSearch();
            }

            @Override
            public void onLoadMore() {
                initSearch();
            }
        });
        initSearch();

    }

    private void initSearch() {
        //得到输入的值

        if (TextUtils.isEmpty(mHomeEdit.getText().toString().trim())) {
            Home_Shop_List.setVisibility(View.INVISIBLE);
            XBanner.setVisibility(View.VISIBLE);
            Hot_Lift_Fashion.setVisibility(View.VISIBLE);
            OneTwoMenu.setVisibility(View.VISIBLE);
            Home_Search_Error_Text.setVisibility(View.INVISIBLE);
            Home_Search_Error_Image.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(), "请正确输入关键字", Toast.LENGTH_LONG).show();
        } else {
            String inputHomeEdit = mHomeEdit.getText().toString();
            mIPrenserterImp.startGet(APis.HOME_KEYWORD + "?keyword=" + inputHomeEdit + "&page=" + mPage + "&count=" + mCount, Home_Shop_List_Bean.class);
        }
    }


    /**
     * TODO:获取资源ID
     *
     * @param view
     */
    private void initView(View view) {
        mHomeMenu = (ImageView) view.findViewById(R.id.Home_Menu);
        mHomeSearch = (ImageView) view.findViewById(R.id.Home_Search);
        mXBanner = (XBanner) view.findViewById(R.id.xbanner);
        //TODO：qqqqqqqq
        mIPrenserterImp = new IPrenserterImp(this);

    }

    /**
     * TODO：得到数据
     *
     * @param o
     */
    @Override
    public void setData(Object o) {
        if (o instanceof Bean) {

            Bean bean = (Bean) o;
            //热门
            final List<Bean.ResultBean.RxxpBean> rxxp = bean.getResult().getRxxp();
            for (int i = 0; i < rxxp.size(); i++) {
                mCommodityList = rxxp.get(i).getCommodityList();
                mHotAdapter.setMjihe(mCommodityList);
                mHotAdapter.setCallBack(new RecyclerHotAdapter.CallBack() {
                    @Override
                    public void getData(int index) {
                        //网络请求
                         mCommodityId =mCommodityList.get(index).getCommodityId();
                         Log.i("TAG",mCommodityId+"");
                        //热门图片的评价
                        mIPrenserterImp.startGet(APis.HOME_SHOP_COMMENT_LIST + mCommodityId + "&page=" + mPage + "&count=" + mCount, Home_Shop_Comment_List_Bean.class);
                        //热门图片的详情
                        mIPrenserterImp.startGet(APis.HOME_SHOP_DETAILS+"?commodityId="+mCommodityId,Home_Shop_Details_List_Bean.class);

                    }
                });
            }
            //时尚
            List<Bean.ResultBean.MlssBean> mlss = bean.getResult().getMlss();
            for (int i = 0; i < mlss.size(); i++) {
                mCommodityList1 = mlss.get(i).getCommodityList();
                mFationAdapter.setMjihe(mCommodityList1);
                mFationAdapter.setCallBack(new RecyclerHotAdapter.CallBack() {
                    @Override
                    public void getData(int index) {
                        //网络请求
                         mCommodityId =mCommodityList.get(index).getCommodityId();
                        //热门图片的评价
                        mIPrenserterImp.startGet(APis.HOME_SHOP_COMMENT_LIST + mCommodityId + "&page=" + mPage + "&count=" + mCount, Home_Shop_Comment_List_Bean.class);
                        //热门图片的详情
                        mIPrenserterImp.startGet(APis.HOME_SHOP_DETAILS+"?commodityId="+mCommodityId,Home_Shop_Details_List_Bean.class);

                    }
                });
            }
            //生活
            List<Bean.ResultBean.PzshBean> pzsh = bean.getResult().getPzsh();
            for (int i = 0; i < pzsh.size(); i++) {
                List<Bean.ResultBean.PzshBean.CommodityListBeanX> beanXList = pzsh.get(i).getCommodityList();
                mLiftAdapter.setMjihe(beanXList);
                mLiftAdapter.setCallBack(new RecyclerHotAdapter.CallBack() {
                    @Override
                    public void getData(int index) {
                        //网络请求
                         mCommodityId = mCommodityList.get(index).getCommodityId();
                        //热门图片评价
                        mIPrenserterImp.startGet(APis.HOME_SHOP_COMMENT_LIST + mCommodityId + "&page=" + mPage + "&count=" + mCount, Home_Shop_Comment_List_Bean.class);
                        //热门图片的详情
                        mIPrenserterImp.startGet(APis.HOME_SHOP_DETAILS+"?commodityId="+mCommodityId,Home_Shop_Details_List_Bean.class);

                    }
                });

            }
        }
        //XBanner
        if (o instanceof XbannerBean) {
            XbannerBean xbannerBean = (XbannerBean) o;
            mResult = xbannerBean.getResult();
            initXbanner();
        }
        //一级分类
        if (o instanceof Home_Menu_One_Bean) {
            final Home_Menu_One_Bean menuOneBean = (Home_Menu_One_Bean) o;
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
        if (o instanceof Home_Menu_Two_Bean) {
            Home_Menu_Two_Bean menuTwoBean = (Home_Menu_Two_Bean) o;
            mMenuTwoAdapter.setMjihe(menuTwoBean.getResult());
            mMenuTwoAdapter.setCallBack(new Home_Menu_Two_Adapter.CallBack() {
                @Override
                public void callBack(int i) {
                    //TODO:详细类列商品表
                    initListShop(i);
                }
            });
        }

        //详细分类商品列表
        if (o instanceof Home_Shop_List_Bean) {
            final Home_Shop_List_Bean homeShopListBean = (Home_Shop_List_Bean) o;
            //
            if (homeShopListBean.getResult().size() > 0) {
                if (mPage == 1) {
                    mShopListAdapter.setMjihe(homeShopListBean.getResult());
                    Home_Search_Error_Image.setVisibility(View.GONE);
                } else {
                    mShopListAdapter.addMjihe(homeShopListBean.getResult());
                    mShopListAdapter.notifyDataSetChanged();
                    Home_Search_Error_Image.setVisibility(View.GONE);
                }
                mPage++;
                Home_Shop_List.loadMoreComplete();
                Home_Shop_List.refreshComplete();
                Home_Search_Error_Text.setVisibility(View.INVISIBLE);
                Home_Search_Error_Image.setVisibility(View.INVISIBLE);
                //点击事件：
                mShopListAdapter.setCallBack(new RecyclerHotAdapter.CallBack() {
                    @Override
                    public void getData(int index) {
                        //网络请求
                        mCommodityId = homeShopListBean.getResult().get(index).getCommodityId();
                        //热门图片的评价
                        mIPrenserterImp.startGet(APis.HOME_SHOP_COMMENT_LIST + mCommodityId + "&page=" + mPage + "&count=" + mCount, Home_Shop_Comment_List_Bean.class);
                        //热门图片的详情
                        mIPrenserterImp.startGet(APis.HOME_SHOP_DETAILS+"?commodityId="+mCommodityId,Home_Shop_Details_List_Bean.class);

                    }
                });
                if (homeShopListBean.getResult().size()<5){
                    Toast.makeText(getActivity(), "已刷新到最后", Toast.LENGTH_SHORT).show();
                    Home_Shop_List.setLoadingMoreEnabled(false);
                }

            }else {
                //查询失败!!!
                //图片和文字显示，其余隐藏
                Home_Search_Error_Text.setVisibility(View.VISIBLE);
                Home_Search_Error_Image.setVisibility(View.VISIBLE);
                XBanner.setVisibility(View.INVISIBLE);
                Hot_Lift_Fashion.setVisibility(View.INVISIBLE);
                OneTwoMenu.setVisibility(View.INVISIBLE);

            }
        }

        //商品评价列表
        if (o instanceof Home_Shop_Comment_List_Bean) {
            Home_Shop_Comment_List_Bean homeShopCommentListBean = (Home_Shop_Comment_List_Bean) o;
            //得到数据集合，
            List<Home_Shop_Comment_List_Bean.ResulteBean> result = homeShopCommentListBean.getResult();
                    mHomeShopDetailsRecyclerAdapter.setMjihe(result);
                    //显示
                    home_shop_details.setVisibility(View.VISIBLE);
                    Home_All.setVisibility(View.GONE);
        }
    //商品详情列表
        if (o instanceof  Home_Shop_Details_List_Bean){
            Home_Shop_Details_List_Bean homeShopDetailsListBean=(Home_Shop_Details_List_Bean)o;
          //  Home_Shop_Details_Image；
            String picture = homeShopDetailsListBean.getResult().getPicture();
            String[] split = picture.split("\\,");
            String image = split[0];
           Glide.with(getActivity()).load(image).into(Home_Shop_Details_Image);
            //显示
            home_shop_details.setVisibility(View.VISIBLE);
            Home_All.setVisibility(View.GONE);
        }
        //查询购物车 TODO:!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if(o instanceof Fragment_Shop_Car_Bean){
            Fragment_Shop_Car_Bean fragmentShopCarBean=(Fragment_Shop_Car_Bean)o;
            //如果查询成功
            List<Query_Shop_Car> queryShopCars=new ArrayList<>();

            if (fragmentShopCarBean.getStatus().equals("0000")){
                List<Fragment_Shop_Car_Bean.ResultBean> result = fragmentShopCarBean.getResult();
                //将result的CommodityId、count保存到新的集合

                for (int i = 0; i <result.size() ; i++) {
                    int commodityId = result.get(i).getCommodityId();
                    int count = result.get(i).getCount();
                   Query_Shop_Car query_shop_car=new Query_Shop_Car(commodityId,count);
//                   query_shop_car.setCommodityId(commodityId);
//                   query_shop_car.setCount(count);
                   queryShopCars.add(query_shop_car);
                }
                addShopCar(queryShopCars);
            }else {
                Toast.makeText(getActivity(),"查询购物车失败",Toast.LENGTH_LONG).show();
                addShopCar(queryShopCars);
            }
        }


        //将商品添加到购物车
        if (o instanceof RegistBean){
            RegistBean registBean=(RegistBean)o;
            if (registBean.getStatus().equals("0000")){
                Toast.makeText(getActivity(), registBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * TODO:将查询到的购物车数据进行展示!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * @param queryShopCars
     */
    private void addShopCar(List<Query_Shop_Car> queryShopCars) {
        if (queryShopCars.size()==0){
            //将选中的添加到集合
                    queryShopCars.add( new Query_Shop_Car(Integer.valueOf(mCommodityId), 1));
        }else {
            for (int i = 0; i < queryShopCars.size(); i++) {
                //如果商品的ID一样，则数量改变
                if (queryShopCars.get(i).getCommodityId() == mCommodityId) {
                    int count = queryShopCars.get(i).getCount();
                    count++;
                    queryShopCars.get(i).setCount(count);
                    break;
                } else if (i == queryShopCars.size() - 1) {
                    queryShopCars.add(new Query_Shop_Car(Integer.valueOf(mCommodityId), 1));
                    Log.i("TAG", "是的法规");
                    break;
                }
            }

                Gson gson = new Gson();
           String toJson = gson.toJson(queryShopCars);
                HashMap<String, String> map = new HashMap<>();
                map.put("data", toJson);
                //请求网络
                mIPrenserterImp.startPut(map, APis.HOME_ADD_SHOP_CAR, RegistBean.class);


        }
    }



    @BindView(R.id.Home_Shop_Details)
        RelativeLayout home_shop_details;
    @BindView(R.id.home_all)
    RelativeLayout Home_All;
    @BindView(R.id.Home_Shop_Details_Recycler)
    RecyclerView Home_Shop_Details_Recycler;
    private void shopDetails() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        Home_Shop_Details_Recycler.setLayoutManager(manager);
        //适配器：
        mHomeShopDetailsRecyclerAdapter = new Home_Shop_Details_Recycler_Adapter(getActivity());
        Home_Shop_Details_Recycler.setAdapter(mHomeShopDetailsRecyclerAdapter);
    }

    /**
     * TODO:详细分类商品列表
     */
    @BindView(R.id.Home_Shop_List)
    XRecyclerView Home_Shop_List;
    int mPage = 1;
    @BindView(R.id.xbanner)
    XBanner XBanner;
    @BindView(R.id.Hot_Lift_Fashion)
    RelativeLayout Hot_Lift_Fashion;
    @BindView(R.id.OneTwoMenu)
    RelativeLayout OneTwoMenu;

    private void initListShop(final int i) {
        //XRecycler
        initShopListXRecycler();
        Home_Shop_List.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override       //刷新
            public void onRefresh() {
                mPage = 1;
                //请求网络
                initXRecycler(i, mPage);
                Home_Search_Error_Image.setVisibility(View.INVISIBLE);
                Home_Shop_List.refreshComplete();
            }

            @Override       //加载
            public void onLoadMore() {
                //请求网络
                initXRecycler(i, mPage);
                Home_Shop_List.loadMoreComplete();

            }
        });
        initXRecycler(i, mPage);
    }

    /**
     * TODO:商品列表XRecycler的布局、适配器
     */
    private void initShopListXRecycler() {
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
        Home_Search_Error_Text.setVisibility(View.INVISIBLE);
        Home_Search_Error_Image.setVisibility(View.INVISIBLE);
        //设置刷新加载
        Home_Shop_List.setLoadingMoreEnabled(true);
        Home_Shop_List.setPullRefreshEnabled(true);
    }

    /**
     * TODO：商品列表的网络请求
     */
    int mCount = 5;

    private void initXRecycler(int i, int mpage) {
        mIPrenserterImp.startGet(APis.HOME_SHOP_LIST + "categoryId=" + i + "&page=" + 1 + "&count=" + mCount, Home_Shop_List_Bean.class);

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
        mIPrenserterImp.startGet(APis.HOME_MENU_TWO + i, Home_Menu_Two_Bean.class);
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
        int Initial_Value = 1001002;
        mIPrenserterImp.startGet(APis.HOME_MENU_TWO + Initial_Value, Home_Menu_Two_Bean.class);
    }


    /**
     * TODO:XBanner展示
     */
    List<String> mXbanner_list;

    private void initXbanner() {
        mXbanner_list = new ArrayList<>();
        for (int i = 0; i < mResult.size(); i++) {
            String imageUrl = mResult.get(i).getImageUrl();
            mXbanner_list.add(imageUrl);
        }
        // 为XBanner绑定数据
        //第二个参数为提示文字资源集合
        mXBanner.setData(mXbanner_list, null);
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
     *
     * @param error
     */
    @Override
    public void setError(String error) {
            Toast.makeText(getActivity(),"error:"+error,Toast.LENGTH_LONG).show();
    }
    /*Multipart body must have at least one part*/

    /**
     * 解绑
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mIPrenserterImp.onDelet();
    }
}
