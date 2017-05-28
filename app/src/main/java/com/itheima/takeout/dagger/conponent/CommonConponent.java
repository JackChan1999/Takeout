package com.itheima.takeout.dagger.conponent;

import com.itheima.takeout.dagger.module.PresenterModule;
import com.itheima.takeout.ui.activity.BaseActivity;

import dagger.Component;

/**
 * Created by itheima.
 */
@Component(modules = PresenterModule.class)
public interface CommonConponent {
    void in(BaseActivity view);// in 的对象？  需要有@Inject的类
}
