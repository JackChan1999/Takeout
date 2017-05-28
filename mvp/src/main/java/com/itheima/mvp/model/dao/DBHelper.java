package com.itheima.mvp.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.itheima.mvp.model.dao.bean.AddressBean;
import com.itheima.mvp.model.dao.bean.UserBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Teacher on 2016/9/2.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper{
    private static final String DATABASENAME = "itheima.db";
    private static final int DATABASEVERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
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
