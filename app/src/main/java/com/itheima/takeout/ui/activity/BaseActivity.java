package com.itheima.takeout.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.itheima.takeout.dagger.conponent.DaggerCommonConponent;
import com.itheima.takeout.dagger.module.PresenterModule;
import com.itheima.takeout.presenter.activity.AddressPresenter;
import com.itheima.takeout.presenter.activity.LoginActivityPresenter;
import com.itheima.takeout.presenter.activity.OrderPresenter;
import com.itheima.takeout.presenter.activity.PaymentPresenter;
import com.itheima.takeout.ui.IView;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

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
 * des ：外卖客户端
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/30 13:52
 * updateDes：${TODO}
 * ============================================================
 */

public class BaseActivity extends AppCompatActivity implements IView {
    @Inject
    AddressPresenter addressPresenter;

    @Inject
    OrderPresenter orderPresenter;

    @Inject
    PaymentPresenter paymentPresenter;

    @Inject
    LoginActivityPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerCommonConponent.builder().presenterModule(new PresenterModule(this)).build().inject(this);
    }

    @Override
    public void success(Object o) {

    }

    @Override
    public void failed(String msg) {

    }

    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);

        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }


}
