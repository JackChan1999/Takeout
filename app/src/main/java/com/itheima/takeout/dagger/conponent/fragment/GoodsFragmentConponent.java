package com.itheima.takeout.dagger.conponent.fragment;

import com.itheima.takeout.dagger.module.fragment.GoodsFragmentModule;
import com.itheima.takeout.ui.fragment.GoodsFragment;

import dagger.Component;
import dagger.Module;

/**
 * Created by itheima.
 */
@Component(modules = GoodsFragmentModule.class)
public interface GoodsFragmentConponent {
    void in(GoodsFragment fragment);
}
