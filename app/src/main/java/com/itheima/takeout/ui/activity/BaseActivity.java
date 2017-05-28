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
 * Activity公共操作的封装
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
        DaggerCommonConponent.builder().presenterModule(new PresenterModule(this)).build().in(this);
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
