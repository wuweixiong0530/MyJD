package com.bwie.myapplication.utils;

public class Constant {
    public static final String BASE_URL = "https://www.zhaoapi.cn/";
    //首页
    public static final String HOME_URL = "ad/getAd";
    //分类
    public static final String FEN_LEI_URL = "product/getCatagory";
    //详情
    public static final String DETAIL_URL = "product/getProductDetail";
    //子分类
    public static final String CHILD_FEN_LEI_URL ="product/getProductCatagory";
    //搜索
    public static final String SEARTCH_URL = "product/searchProducts";

    //登录
    public static final String LOGIN_URL = "user/login";
    //查询购物车
    public static final String SELECT_CART = "product/getCarts";
    //更新购物车 product/updateCarts?uid=71&sellerid=1&pid=1&selected=0&num=10
    public static final String UPDATE_CART_URL = "product/updateCarts";
    //删除购物车...product/deleteCart?uid=72&pid=1
    public static final String DELETE_CART_URL ="product/deleteCart";
    //添加购物车...uid...pid
    public static final String ADD_CART_URL = "product/addCart";
    //注册
    public static final String REGIST_URL ="user/reg";
    //https://www.zhaoapi.cn/file/upload上传的服务器路径
    public static final String UPLOAD_ICON_URL = "file/upload";
    //获取用户信息...https://www.zhaoapi.cn/user/getUserInfo
    public static final String USER_INFO_URL = "user/getUserInfo";
    //创建订单...https://www.zhaoapi.cn/product/createOrder
    public static final String CREATE_ORDER_URL = "product/createOrder";
    //订单信息....https://www.zhaoapi.cn/product/getOrders?uid=71
    public static final String ORDER_LIST_URL = "product/getOrders";
    //修改订单状态...https://www.zhaoapi.cn/product/updateOrder?uid=71&status=1&orderId=1
    public static final String UPDATE_ORDER_URL ="product/updateOrder";
    //查询默认地址...https://www.zhaoapi.cn/user/getDefaultAddr?uid=71
    public static final String GET_DEFAULT_ADDR_URL ="user/getDefaultAddr";
    //添加新地址...https://www.zhaoapi.cn/user/addAddr?uid=71&addr=北京市昌平区金域国际1-1-1&mobile=18612991023&name=kson
    public static final String ADD_NEW_ADDR_URL = "user/addAddr";
    //获取地址列表...https://www.zhaoapi.cn/user/getAddrs?uid=71
    public static final String GET_ALL_ADDR_URL = "user/getAddrs";
    //设置默认地址...https://www.zhaoapi.cn/user/setAddr?uid=71&addrid=3&status=1
    public static final String SET_DEFAULT_ADDR_URL = "user/setAddr";

}
