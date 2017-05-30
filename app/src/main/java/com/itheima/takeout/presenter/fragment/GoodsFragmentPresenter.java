package com.itheima.takeout.presenter.fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.takeout.model.net.bean.GoodsTypeInfo;
import com.itheima.takeout.model.net.bean.ResponseInfo;
import com.itheima.takeout.presenter.BasePresenter;
import com.itheima.takeout.ui.fragment.GoodsFragment;

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
 * des ：商品数据的业务处理
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/30 13:52
 * updateDes：${TODO}
 * ============================================================
 */

public class GoodsFragmentPresenter extends BasePresenter {

    private GoodsFragment fragment;

    public GoodsFragmentPresenter(GoodsFragment fragment) {
        this.fragment = fragment;
    }

    public void getData(long sellerId) {
        // 联网获取数据
        Call<ResponseInfo> goods = responseInfoAPI.goods(sellerId);
        goods.enqueue(new CallbackAdapter());
    }


    @Override
    protected void failed(String msg) {

    }

    @Override
    protected void parserData(String data) {
        Gson gson = new Gson();
        ArrayList<GoodsTypeInfo> goodsTypeInfos = gson.fromJson(data, new TypeToken<List<GoodsTypeInfo>>() {
        }.getType());
        // 更新界面
        fragment.success(goodsTypeInfos);
    }
}
