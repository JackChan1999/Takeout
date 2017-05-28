package com.itheima.mvp;

import android.os.SystemClock;

import com.itheima.mvp.model.User;


public class UserLoginNet {

    /**
     * 发送用户输入数据
     * @param user
     * @return
     */
    public boolean sendUserLoginInfo(User user){


        SystemClock.sleep(2000);//模拟登陆耗时操作

        if("itheima".equals(user.username)&&"bj".equals(user.password)){
           return true;

        }else{
            //登陆失败
           return false;
        }

    }
}
