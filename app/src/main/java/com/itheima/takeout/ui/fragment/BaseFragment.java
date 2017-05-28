package com.itheima.takeout.ui.fragment;

import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;

/**
 * Fragment公共信息封装
 */

public class BaseFragment extends Fragment {

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getContext());
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }
}
