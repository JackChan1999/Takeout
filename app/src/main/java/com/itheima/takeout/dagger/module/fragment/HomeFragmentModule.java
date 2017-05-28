package com.itheima.takeout.dagger.module.fragment;

import com.itheima.takeout.presenter.fragment.HomeFragmentPresenter;
import com.itheima.takeout.ui.fragment.HomeFragment;

import dagger.Module;
import dagger.Provides;

/**
 * HomeFragment业务类创建
 */
@Module
public class HomeFragmentModule {
    private HomeFragment fragment;

    public HomeFragmentModule(HomeFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    HomeFragmentPresenter provideHomeFragmentPresenter(){
        return new HomeFragmentPresenter(fragment);
    }
}
