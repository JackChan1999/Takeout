package com.itheima.mvp.presenter;

import com.itheima.mvp.MainActivity;
import com.itheima.mvp.UserLoginNet;
import com.itheima.mvp.model.User;
import com.itheima.mvp.model.net.ResponseInfo;
import com.itheima.mvp.presenter.api.ResponseInfoAPI;
import com.itheima.mvp.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 与登陆相关的业务处理
 */
public class MainActivityPresenter {
    private MainActivity activity;
    private final ResponseInfoAPI api;

    public MainActivityPresenter(MainActivity activity) {
        this.activity = activity;

        // 网络访问：
        // 第一步：创建Builder，指定baseUrl和数据解析工具
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(Constant.BASEURL);
        builder.addConverterFactory(GsonConverterFactory.create());// Gson解析

        // 第二步：创建Retrofit
        Retrofit retrofit = builder.build();

        // 第三步：指定请求方式（get或post）和参数,通过定以接口的形式指定
        // 第三步通过接口com.itheima.mvp.presenter.api.ResponseInfoAPI

        // 第四步：将Retrofit和第三步的联网参数联系起来
        api = retrofit.create(ResponseInfoAPI.class);

    }

    /**
     * 用户登陆
     *
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        Call<ResponseInfo> call = api.login(username, password);
        call.enqueue(new Callback<ResponseInfo>() {
            @Override
            public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                // 处理服务器回复内容
                if (response != null) {
                    if (response.isSuccessful()) {
                        // 登陆成功
                        activity.success();
                    }else{
                        // 错误提示
                    }
                }else{
                    // 错误提示
                    onFailure(call,new RuntimeException("服务器忙请稍后重试"));
                }
            }

            @Override
            public void onFailure(Call<ResponseInfo> call, Throwable t) {
                // 异常处理
                //登陆失败
                activity.failed();
            }
        });
    }


    public void login1(String username, String password) {

        final User user = new User();
        user.username = username;
        user.password = password;
        new Thread() {
            @Override
            public void run() {
                UserLoginNet net = new UserLoginNet();

                if (net.sendUserLoginInfo(user)) {
                    // 登陆成功
                    activity.success();
                } else {
                    //登陆失败
                    activity.failed();
                }

            }
        }.start();
    }
}
