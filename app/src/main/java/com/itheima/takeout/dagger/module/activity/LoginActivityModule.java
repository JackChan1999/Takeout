package com.itheima.takeout.dagger.module.activity;

import com.itheima.takeout.presenter.activity.LoginActivityPresenter;
import com.itheima.takeout.ui.activity.LoginActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by itheima.
 */
@Module
public class LoginActivityModule {
    private LoginActivity activity;

    public LoginActivityModule(LoginActivity activity) {
        this.activity = activity;
    }

    @Provides
    public LoginActivityPresenter provideLoginActivityPresenter(){
        return new LoginActivityPresenter(activity);
    }
}
