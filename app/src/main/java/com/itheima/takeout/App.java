package com.itheima.takeout;

import android.app.Application;
import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.umeng.analytics.MobclickAgent;

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

public class App extends Application {
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
