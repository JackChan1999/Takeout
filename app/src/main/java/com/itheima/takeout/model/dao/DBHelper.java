package com.itheima.takeout.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.itheima.takeout.App;
import com.itheima.takeout.model.dao.bean.AddressBean;
import com.itheima.takeout.model.dao.bean.UserBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

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
public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASENAME = "itheima.db";
    private static final int DATABASEVERSION = 1;

    private DBHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    /**
     * 单例处理
     * 双重校验：提高效率
     * 如果在方法上加锁，每次调用都需要排队
     */

    private static DBHelper instance;


    public static DBHelper getInstance() {
        if (instance == null) {// 第一次校验：提高效率
            // 考虑加锁
            synchronized (DBHelper.class) {
                if (instance == null) {// 第二次校验：防止对象的多次创建
                    instance = new DBHelper(App.getContext());
                    instance.getWritableDatabase();
                }
            }
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserBean.class);
            TableUtils.createTable(connectionSource, AddressBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
