package com.itheima.takeout.ui.observer;

import java.util.HashMap;
import java.util.Observable;

/**
 * Created by itheima.
 */
public class OrderObserver{
    /* 订单状态
     * 1 未支付 2 已提交订单 3 商家接单  4 配送中,等待送达 5已送达 6 取消的订单*/
    public static final String ORDERTYPE_UNPAYMENT = "10";
    public static final String ORDERTYPE_SUBMIT = "20";
    public static final String ORDERTYPE_RECEIVEORDER = "30";
    public static final String ORDERTYPE_DISTRIBUTION = "40";
    public static final String ORDERTYPE_SERVED = "50";
    public static final String ORDERTYPE_CANCELLEDORDER = "60";

}
