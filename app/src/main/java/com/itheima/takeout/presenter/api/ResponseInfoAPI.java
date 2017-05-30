package com.itheima.takeout.presenter.api;



import com.itheima.takeout.model.net.bean.ResponseInfo;
import com.itheima.takeout.utils.Constant;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * ============================================================
 * Copyright：JackChan和他的朋友们有限公司版权所有 (c) 2017
 * Author：   JackChan
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChan1999
 * GitBook：  https://www.gitbook.com/@alleniverson
 * CSDN博客： http://blog.csdn.net/axi295309066
 * 个人博客： https://jackchan1999.github.io/
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：Takeout
 * Package_Name：com.itheima.takeout
 * Version：1.0
 * time：2017/5/30 13:52
 * des ：对请求方式和请求参数的封装
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/30 13:52
 * updateDes：${TODO}
 * ============================================================
 */
public interface ResponseInfoAPI {
    /**
     * 用户登陆:/login?username="jackchan"&password="bj"
     */
//    @GET(Constant.PATH+Constant.LOGIN)

    /**
     * 传统方式登陆
     * @param username
     * @param password
     * @return
     */
    @GET(Constant.LOGIN)
    Call<ResponseInfo> login(
            @Query("username")// 参数的名字
                    String username, // 该参数的值
            @Query("password")
                    String password);

    /**
     * 短信验证的登陆
     * @param phone
     * @param type
     * @return
     */
    @GET(Constant.LOGIN)
    Call<ResponseInfo> login(@Query("phone") String phone,
                             @Query("type") int type);

    /**
     * 获取首页数据
     * @return
     */
    @GET(Constant.HOME)
    Call<ResponseInfo> home();

    /**
     * 获取商品数据
     * @param sellerId（商家标识）
     * @return
     */
    @GET(Constant.GOODS)
    Call<ResponseInfo> goods(@Query("sellerId") long sellerId);

    @GET(Constant.ADDRESS)
    Call<ResponseInfo> address(@Query("userId") int userId);

    @FormUrlEncoded
    @POST(Constant.ORDER)
    Call<ResponseInfo> creatOrder(@Field("orderOverview") String json);

    @GET(Constant.PAY)
    Call<ResponseInfo> payment(@Query("orderId") String orderId);
    @GET(Constant.ORDER)
    Call<ResponseInfo> orderList(@Query("userId") int userid);
}
