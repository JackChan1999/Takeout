package com.itheima.mvp.dagger2.module;

import com.itheima.mvp.MainActivity;
import com.itheima.mvp.presenter.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * 主要用于存放对象创建的代码
 *
 * 第二步：将new MainActivityPresenter(activity);代码放到指定类的指定方法中了
 */
@Module
public class MainActivityModule {


    private MainActivity activity;

    public MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    public MainActivityPresenter provideMainActivityPresenter(){
        return new MainActivityPresenter(activity);
    }
}
