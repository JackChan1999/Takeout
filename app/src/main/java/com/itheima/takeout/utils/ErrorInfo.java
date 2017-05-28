package com.itheima.takeout.utils;

import java.util.HashMap;

/**
 * 存放错误信息
 */

public class ErrorInfo {
    public static HashMap<String, String> INFO = new HashMap<>();
    static{
        INFO.put("0","成功");
        INFO.put("1","用户名或密码有误");
    }
}
