package com.itheima.takeout.dagger.conponent.fragment;

import com.itheima.takeout.dagger.module.PresenterModule;
import com.itheima.takeout.ui.fragment.OrderFragment;

import dagger.Component;

/**
 * Created by itheima.
 */
@Component(modules = PresenterModule.class)
public interface OrderFragmentConponent {
    void in(OrderFragment fragment);
}
