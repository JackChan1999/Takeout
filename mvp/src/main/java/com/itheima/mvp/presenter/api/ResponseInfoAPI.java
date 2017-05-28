package com.itheima.mvp.presenter.api;

import com.itheima.mvp.model.net.ResponseInfo;
import com.itheima.mvp.utils.Constant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 对请求方式和请求参数的封装
 */
public interface ResponseInfoAPI {
    /**
     * 用户登陆:/login?username="itheima"&password="bj"
     */
//    @GET(Constant.PATH+Constant.LOGIN)
    @GET(Constant.LOGIN)
    Call<ResponseInfo> login(
            @Query("username")// 参数的名字
            String username, // 该参数的值
            @Query("password")
            String password);
}
