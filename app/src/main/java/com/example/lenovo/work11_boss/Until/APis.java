package com.example.lenovo.work11_boss.Until;

/**
 * @author lenovo
 *  各种网址接口的后半部分
 */
public class APis {


    //登录：
    public static final String MAIN_LOGIN="user/v1/login";
    //注册：
    public static final String REGIST_NET="user/v1/register";
    //用户也轮播图：
    public static final String HOME_RECYCLER_BANNER="commodity/v1/bannerShow";
    //一级商品类目查询
    public static final String HOME_MENU="commodity/v1/findFirstCategory";
    //二级商品类目查询
    public static final String HOME_MENU_TWO="commodity/v1/findSecondCategory?firstCategoryId=";
    //详细商品列表
    public static final String HOME_SHOP_LIST="commodity/v1/findCommodityByCategory?";
    //热门
    public static final String HOME_HOT="commodity/v1/commodityList";
    //圈子
    public static final String FIND_PATH="circle/v1/findCircleList";
    //获取个人信息
    public static final String MAIN_PROFILE="user/verify/v1/getUserById";
    //我的圈子
    public static final String MAIN_FIND="circle/verify/v1/findMyCircleById";
    //根据关键字搜索
    public static final String HOME_KEYWORD="commodity/v1/findCommodityByKeyword";
}
