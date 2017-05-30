package com.itheima.takeout.presenter.activity;

import android.util.Log;

import com.google.gson.Gson;
import com.itheima.takeout.App;
import com.itheima.takeout.model.dao.bean.UserBean;
import com.itheima.takeout.model.net.bean.ResponseInfo;
import com.itheima.takeout.presenter.BasePresenter;
import com.itheima.takeout.ui.IView;
import com.itheima.takeout.utils.Constant;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import retrofit2.Call;


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
 * des ：用户登录界面业务管理
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/30 13:52
 * updateDes：${TODO}
 * ============================================================
 */

public class LoginActivityPresenter extends BasePresenter {
    IView view;

    public LoginActivityPresenter(IView view) {
        this.view =view;
    }

    @Override
    protected void failed(String msg) {
        view.failed(msg);
    }

    @Override
    protected void parserData(String data) {
        Log.i("TEST", data);
        // 将data存储到数据库中，用户表

        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(data, UserBean.class);

        // 存储数据

        AndroidDatabaseConnection connection = null;
        Savepoint start = null;
        try {
            Dao<UserBean, ?> dao = helper.getDao(UserBean.class);


            // 在添加新的已经登陆用户数据时，需要检查本地数据库中的用户信息，确保他们都是未登录的状态

            // 工作内容：
            // 查询所有的用户数据，修改登陆状态为false
            // 添加新用户，设置状态为true

            // 事务操作的流程：开启事务     一系列数据库操作     提交    回滚
            // 问题：1.回滚到那里（还原点的设置）；2.一系列数据库操作不能立即生效

            connection = new AndroidDatabaseConnection(helper.getReadableDatabase(), true);


            // 1.开启事务   还原点的设置
            start = connection.setSavePoint("start");// 设置还原点的时候有开启事务的操作
            // 2.一系列数据库操作不能立即生效
            dao.setAutoCommit(connection, false);// 关闭立即提交（需要进行一些列的操作）
            // 3.查询所有的用户数据，修改登陆状态为false
            List<UserBean> userBeen = dao.queryForAll();
            if (userBeen != null && userBeen.size() > 0) {
                for (UserBean item :
                        userBeen) {
                    item.login = false;
                    dao.update(item);
                }
            }
            // 4.添加新用户，设置状态为true
            userBean.login = true;// 已经登陆
            dao.create(userBean);

            connection.commit(start);

            App.phone = userBean.phone;
            App.USERID = userBean._id;
            view.success(null);

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(start);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            failed("修改本地数据异常");
        }


    }

    /**
     * 获取用户数据
     *
     * @param phone
     */
    public void getData(String phone) {
//        phone	电话
//        type	登陆类型（必须）

        Call<ResponseInfo> login = responseInfoAPI.login(phone, Constant.LOGIN_TYPE_SMS);
        login.enqueue(new CallbackAdapter());
    }
}
