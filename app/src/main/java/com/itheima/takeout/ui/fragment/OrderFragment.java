package com.itheima.takeout.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.takeout.R;
import com.itheima.takeout.dagger.conponent.fragment.DaggerOrderFragmentConponent;
import com.itheima.takeout.dagger.module.PresenterModule;
import com.itheima.takeout.model.net.bean.Order;
import com.itheima.takeout.presenter.activity.OrderPresenter;
import com.itheima.takeout.ui.IView;
import com.itheima.takeout.ui.adapter.OrderRecyclerViewAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
 * des ：订单列表
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/30 13:52
 * updateDes：${TODO}
 * ============================================================
 */
public class OrderFragment extends BaseFragment implements IView{
    @BindView(R.id.rv_order_list)
    RecyclerView rvOrderList;
    private Unbinder unbinder;

    OrderRecyclerViewAdapter adapter;

    @Inject
    OrderPresenter orderPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerOrderFragmentConponent.builder().presenterModule(new PresenterModule(this)).build().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter=new OrderRecyclerViewAdapter();
        rvOrderList.setAdapter(adapter);
        rvOrderList.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void onResume() {
        super.onResume();
        orderPresenter.getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void success(Object o) {
        if(o instanceof List){
            adapter.setOrders((List<Order>) o);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void failed(String msg) {

    }
}
