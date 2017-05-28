package com.itheima.takeout.presenter.activity;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.takeout.MyApplication;
import com.itheima.takeout.model.dao.bean.AddressBean;
import com.itheima.takeout.model.dao.bean.UserBean;
import com.itheima.takeout.model.net.bean.ResponseInfo;
import com.itheima.takeout.presenter.BasePresenter;
import com.itheima.takeout.ui.IView;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;

/**
 * 地址的业务管理
 */

public class AddressPresenter extends BasePresenter {

    // 地址的增、删、改、查
    static Dao<AddressBean, Integer> dao;
    private IView view;

    public AddressPresenter(IView view) {
        this.view = view;

        try {
            if (dao == null)
                dao = helper.getDao(AddressBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void getData() {
        // 获取地址数据：
        // 获取网络地址，获取本地地址
        // 如果网络地址获取到后，存储到本地

        Call<ResponseInfo> address = responseInfoAPI.address(MyApplication.USERID);
        address.enqueue(new CallbackAdapter());
    }


    @Override
    protected void failed(String msg) {

    }

    @Override
    protected void parserData(String data) {
        if (TextUtils.isEmpty(data)) {
            // 读取本地的地址
            findAllByUserId(MyApplication.USERID);
        } else {
            Gson gson=new Gson();
            // 获取网络信息记录入库
            List<AddressBean> addressBeen = gson.fromJson(data,new TypeToken<List<AddressBean>>(){}.getType());

            for(AddressBean item:addressBeen){
                create(item);
            }

            findAllByUserId(MyApplication.USERID);
        }
    }

    /**
     * 添加一条地址记录(将服务器的数据插入到本地数据库中)
     * @param item
     */
    public int create(AddressBean item){
        UserBean userBean=new UserBean();
        userBean._id=MyApplication.USERID;
        item.user=userBean;
        try {
            return dao.create(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 用户输入信息添加
     */
    public void create(String name, String sex, String phone, String receiptAddress, String detailAddress, String label, double longitude, double latitude){
        // 写入本地数据库
        AddressBean bean=new AddressBean(name,sex,phone,receiptAddress,detailAddress,label,longitude,latitude);
        int i = create(bean);
        if(i==1){
            // 写入本地数据库操作成功
            view.success(i);// 添加地址界面
        }else{
            view.failed("添加操作失败");
        }
        // 发送数据到网络
    }

    /**
     * 依据用户标识获取对应的地址信息
     *
     * @param userId
     */
    public void findAllByUserId(int userId) {
        try {
            List<AddressBean> addressBeen = dao.queryForEq("user_id", userId);
            view.success(addressBeen);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void findById(int id) {
        try {
            AddressBean addressBean = dao.queryForId(id);
            view.success(addressBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, String name, String sex, String phone, String receiptAddress, String detailAddress, String label, double longitude, double latitude) {

        AddressBean bean = new AddressBean(name, sex, phone, receiptAddress, detailAddress, label,longitude,latitude);
        UserBean user=new UserBean();
        user._id=MyApplication.USERID;
        bean.user=user;
        bean._id=id;

        try {
            int update = dao.update(bean);
            if(update==1){
                view.success(update);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * 依据标识删除地址
     * @param id
     */
    public void delete(int id) {
        try {
            int i = dao.deleteById(id);
            if(i==1){
                view.success(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
