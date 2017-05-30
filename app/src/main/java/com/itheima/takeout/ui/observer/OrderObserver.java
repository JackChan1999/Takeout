package com.itheima.takeout.ui.observer;

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
 * des ：订单状态
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/30 13:52
 * updateDes：${TODO}
 * ============================================================
 */
public class OrderObserver {
    /* 订单状态
     * 1 未支付
     * 2 已提交订单
     * 3 商家接单
     * 4 配送中,等待送达
     * 5 已送达
     * 6 取消的订单*/
    public static final String ORDERTYPE_UNPAYMENT      = "10";
    public static final String ORDERTYPE_SUBMIT         = "20";
    public static final String ORDERTYPE_RECEIVEORDER   = "30";
    public static final String ORDERTYPE_DISTRIBUTION   = "40";
    public static final String ORDERTYPE_SERVED         = "50";
    public static final String ORDERTYPE_CANCELLEDORDER = "60";

}
