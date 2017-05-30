package com.itheima.takeout.presenter;

import com.itheima.takeout.model.dao.DBHelper;
import com.itheima.takeout.model.net.bean.ResponseInfo;
import com.itheima.takeout.presenter.api.ResponseInfoAPI;
import com.itheima.takeout.utils.Constant;
import com.itheima.takeout.utils.ErrorInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
 * des ：业务层公共代码封装
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/30 13:52
 * updateDes：${TODO}
 * ============================================================
 */

public abstract class BasePresenter {
    protected static ResponseInfoAPI responseInfoAPI;
    // 数据库
    // 网络
    protected DBHelper helper;


    public BasePresenter() {

/*        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(Constant.BASEURL);
        builder.addConverterFactory(GsonConverterFactory.create());// Gson解析

        Retrofit retrofit = builder.build();*/


        // 第一次初始化完成后，所有子类都可以使用
        if(responseInfoAPI==null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            responseInfoAPI = retrofit.create(ResponseInfoAPI.class);
        }

        helper=DBHelper.getInstance();

    }

    public class CallbackAdapter implements Callback<ResponseInfo>{

        @Override
        public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
            // 处理回复
            if (response != null && response.isSuccessful()) {
                ResponseInfo info = response.body();

                if("0".equals(info.code)){
                    // 服务器端处理成功，并返回目标数据
                    parserData(info.data);
                }else{
                    // 服务器端处理成功，返回错误提示，该信息需要展示给用户
                    // 依据code值获取到失败的数据
                    String msg = ErrorInfo.INFO.get(info.code);
                    failed(msg);
                }

            } else {
                // 联网过程中的异常
            }


        }

        @Override
        public void onFailure(Call<ResponseInfo> call, Throwable t) {
            // 联网过程中的异常
        }
    }

    /**
     * 错误处理
     * @param msg
     */
    protected abstract void failed(String msg);

    /**
     * 解析服务器回复数据
     * @param data
     */
    protected abstract void parserData(String data);
}
