package com.itheima.takeout.presenter.activity;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.takeout.App;
import com.itheima.takeout.model.net.bean.Cart;
import com.itheima.takeout.model.net.bean.GoodsInfo;
import com.itheima.takeout.model.net.bean.Order;
import com.itheima.takeout.model.net.bean.OrderOverview;
import com.itheima.takeout.model.net.bean.ResponseInfo;
import com.itheima.takeout.presenter.BasePresenter;
import com.itheima.takeout.ui.IView;
import com.itheima.takeout.ui.ShoppingCartManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

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
 * des ：订单业务处理类
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/30 13:52
 * updateDes：${TODO}
 * ============================================================
 */

public class OrderPresenter extends BasePresenter {
    // 1.生成订单——结算中心
    // 2.订单列表查询——订单列表界面
    // 3.订单详情查询——详情展示

    private IView view;

    public OrderPresenter(IView view) {
        this.view = view;
    }

    @Override
    protected void failed(String msg) {

    }

    @Override
    protected void parserData(String data) {
        Log.i("Test", data);
        switch(operation){
            case 1:
                view.success(data);
                break;
            case 2:
                Gson gson=new Gson();
                List<Order> orders=gson.fromJson(data,new TypeToken<List<Order>>(){}.getType());
                view.success(orders);
                break;
        }

    }

    int operation = 0;// 操作的标识

    public void create(int userid, int addressId, int type) {
        operation = 1;

        OrderOverview overview = new OrderOverview();
        overview.addressId = addressId;
        overview.sellerid = ShoppingCartManager.getInstance().sellerId;
        overview.type = type;
        overview.userId = userid;

        overview.cart = new ArrayList<>();
        for (GoodsInfo info : ShoppingCartManager.getInstance().goodsInfos) {
            Cart cart = new Cart();
            cart.id = info.id;
            cart.count = info.count;

            overview.cart.add(cart);
        }


        // 发送数据到服务器端（POST）
        Gson gson = new Gson();
        Call<ResponseInfo> order = responseInfoAPI.creatOrder(gson.toJson(overview));
        order.enqueue(new CallbackAdapter());

    }

    /**
     * 获取订单列表
     */
    public void getData() {
        operation = 2;
        Call<ResponseInfo> call = responseInfoAPI.orderList(App.USERID);
        call.enqueue(new CallbackAdapter());
    }
}
