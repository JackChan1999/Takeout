package com.itheima.takeout.utils;

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
public interface Constant {
    // http://localhost:8080/   TakeoutService    /login?username="itheima"&password="bj"

    String BASEURL="http://127.0.0.1:8090/";
    // 登陆
    String LOGIN="TakeoutService/login";
    // http://localhost:8080/TakeoutService/home
    String HOME="TakeoutService/home";
    // http://localhost:8080/TakeoutService/goods?sellerId=1
    String GOODS = "TakeoutService/goods";
    //    http://localhost:8080/TakeoutService/address?userId=2163&&&&&&
    String ADDRESS="TakeoutService/address";

    String ORDER = "TakeoutService/order";
    String PAY="TakeoutService/pay";

    // 短信登陆的分类值
    int LOGIN_TYPE_SMS=2;

}
