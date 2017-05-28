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

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 订单列表
 */
public class OrderFragment extends BaseFragment implements IView{
    @InjectView(R.id.rv_order_list)
    RecyclerView rvOrderList;

    OrderRecyclerViewAdapter adapter;

    @Inject
    OrderPresenter orderPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerOrderFragmentConponent.builder().presenterModule(new PresenterModule(this)).build().in(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, null);
        ButterKnife.inject(this, view);
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
        ButterKnife.reset(this);
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
