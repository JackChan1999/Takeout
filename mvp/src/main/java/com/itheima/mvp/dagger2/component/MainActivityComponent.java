package com.itheima.mvp.dagger2.component;

import com.itheima.mvp.MainActivity;
import com.itheima.mvp.dagger2.module.MainActivityModule;

import dagger.Component;

/**
 * 第三步：通过接口将创建实例的代码和目标关联在一起“=”
 */
@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {
    void in(MainActivity activity);
}
