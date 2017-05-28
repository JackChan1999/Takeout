package com.itheima.takeout.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.takeout.R;
import com.itheima.takeout.ui.activity.LoginActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 用户信息展示界面
 */
public class UserFragment extends BaseFragment {
    @InjectView(R.id.tv_user_setting)
    ImageView tvUserSetting;
    @InjectView(R.id.iv_user_notice)
    ImageView ivUserNotice;
    @InjectView(R.id.login)
    ImageView login;
    @InjectView(R.id.username)
    TextView username;
    @InjectView(R.id.phone)
    TextView phone;
    @InjectView(R.id.ll_userinfo)
    LinearLayout llUserinfo;
    @InjectView(R.id.address)
    ImageView address;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.login)
    public void onClick() {
        Intent intent = new Intent(this.getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
