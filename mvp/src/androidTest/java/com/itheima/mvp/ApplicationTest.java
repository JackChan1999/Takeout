package com.itheima.mvp;

import android.app.Application;
import android.test.ApplicationTestCase;


import com.itheima.mvp.model.dao.DBHelper;
import com.itheima.mvp.model.dao.bean.AddressBean;
import com.itheima.mvp.model.dao.bean.UserBean;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testCreateDB(){
        DBHelper dbHelper=new DBHelper(getContext());
        dbHelper.getWritableDatabase();
    }

    public void testUser() throws SQLException {
        DBHelper dbHelper=new DBHelper(getContext());
        Dao<UserBean, Integer> dao = dbHelper.getDao(UserBean.class);
        UserBean userBean1=new UserBean();
        userBean1.set_id(1);
        dao.create(userBean1);

        UserBean userBean2=new UserBean();
        userBean2.set_id(2);

        dao.create(userBean2);

    }

    public void testAddress() throws SQLException {

        DBHelper dbHelper=new DBHelper(getContext());
        Dao<AddressBean, Integer> dao = dbHelper.getDao(AddressBean.class);

        UserBean userBean=new UserBean();
        userBean.set_id(2);

        for(int i=1;i<10;i++) {
            AddressBean addressBean=new AddressBean();
            addressBean.set_id(i);
            addressBean.setGoodsAddress("送货地址"+i);
            addressBean.setVillage("xiaoqu"+i);

            addressBean.setUser(userBean);
            dao.create(addressBean);
        }

    }

    public void testFindById() throws SQLException {
        DBHelper dbHelper=new DBHelper(getContext());
        Dao<UserBean, Integer> dao = dbHelper.getDao(UserBean.class);

        UserBean userBean = dao.queryForId(2);
        System.out.println(userBean.toString());
    }
}