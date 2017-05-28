package com.itheima.takeout;

import android.app.Application;
import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by itheima.
 */

public class MyApplication extends Application {
    public static LatLonPoint LOCATION = null;
    private static Context context;

    public static Context getContext() {
        return context;
    }


    // 记录用户信息
//    public static int USERID=0;
//    public static String phone="";
    // 测试数据
    public static int USERID=2163;
    public static String phone="13280000000";

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;

        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);

        // 关闭Activity的默认统计方式，我们需要统计Activity+Fragment
        MobclickAgent.openActivityDurationTrack(false);
    }
}
