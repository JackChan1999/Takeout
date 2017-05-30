package com.itheima.takeout.dagger.module.fragment;

import com.itheima.takeout.presenter.fragment.GoodsFragmentPresenter;
import com.itheima.takeout.ui.fragment.GoodsFragment;

import dagger.Module;
import dagger.Provides;

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
@Module
public class GoodsFragmentModule {
    private GoodsFragment fragment;

    public GoodsFragmentModule(GoodsFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    public GoodsFragmentPresenter provideGoodsFragmentPresenter(){
        return new GoodsFragmentPresenter(fragment);
    }
}
