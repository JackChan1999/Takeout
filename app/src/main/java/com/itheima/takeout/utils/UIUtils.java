package com.itheima.takeout.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

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

public class UIUtils {
    public static int STATUE_BAR_HEIGHT=0;// 记录装填栏的高度

    /**
     * 依据Id查询指定控件的父控件
     * @param v 指定控件
     * @param id 父容器标识
     * @return
     */
    public static ViewGroup getContainder(View v, int id) {
        ViewGroup parent = (ViewGroup) v.getParent();
        if (parent.getId() == id) {
            return parent;
        }
        return getContainder(parent, id);
    }

    public static int dp2px(Context context, int dp){
        return (int) (dp*context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
