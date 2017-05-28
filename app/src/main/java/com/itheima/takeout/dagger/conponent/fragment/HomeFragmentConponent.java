package com.itheima.takeout.dagger.conponent.fragment;

import com.itheima.takeout.dagger.module.fragment.HomeFragmentModule;
import com.itheima.takeout.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * 将创建好的业务对象设置给目标
 */
@Component(modules = HomeFragmentModule.class)
public interface HomeFragmentConponent {
    void in(HomeFragment fragment);
}
