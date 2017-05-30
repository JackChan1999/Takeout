package com.itheima.takeout.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.takeout.R;
import com.itheima.takeout.dagger.conponent.fragment.DaggerGoodsFragmentConponent;
import com.itheima.takeout.dagger.module.fragment.GoodsFragmentModule;
import com.itheima.takeout.model.net.bean.GoodsInfo;
import com.itheima.takeout.model.net.bean.GoodsTypeInfo;
import com.itheima.takeout.presenter.fragment.GoodsFragmentPresenter;
import com.itheima.takeout.ui.ShoppingCartManager;
import com.itheima.takeout.ui.activity.CartActivity;
import com.itheima.takeout.ui.adapter.StickyListAdapter;
import com.itheima.takeout.ui.adapter.HeadAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
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

/**
 * 订单列表
 * <p>
 * 1.组信息展示
 * 2.头信息展示
 * 3.联动
 * a.在头容器中点击某个条目的时候，让该组信息在条目容器中置顶
 * b.在滚动条目容器，让头容器针对置顶组进行调整
 * 注意事项：频繁的刷新头容器
 */
public class GoodsFragment extends BaseFragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    @BindView(R.id.shl)
    StickyListHeadersListView shl;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.fragment_goods_tv_count)
    TextView fragmentGoodsTvCount;
    @BindView(R.id.cart)
    RelativeLayout cart;

    private Unbinder unbinder;

    private StickyListAdapter groupAdapter;
    private HeadAdapter       headAdapter;

    @Inject
    GoodsFragmentPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerGoodsFragmentConponent.builder()
                .goodsFragmentModule(new GoodsFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods, container,false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 判断购物车中是否有商品，如果有需要对购物车的气泡进行修改
        Integer totalNum = ShoppingCartManager.getInstance().getTotalNum();
        if(totalNum>0){
            fragmentGoodsTvCount.setVisibility(View.VISIBLE);
            fragmentGoodsTvCount.setText(totalNum.toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getData(getArguments().getLong("seller_id"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 在头容器中点击某个条目的时候，让该组信息在条目容器中置顶
        // 1.高亮点击条目
        headAdapter.setSelectedPositon(position);
        GoodsTypeInfo head = headDatas.get(position);
        shl.setSelection(head.groupFirstIndex);
        isScroll = false;
    }

    private boolean isScroll = false;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        System.out.println("scrollState:" + scrollState);
        // 用户在滚动
        isScroll = true;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 用户的滚动
        if (isScroll) {
            System.out.println("firstVisibleItem:" + firstVisibleItem);

            GoodsInfo data = datas.get(firstVisibleItem);
            System.out.println("data.headIndex:" + data.headIndex);
            // 当前正在置顶显示的头高亮处理
            headAdapter.setSelectedPositon(data.headIndex);

            // 判断头容器对应的条目是否处于可见状态
            // 获取到第一个可见，和最后一个可见的。比第一个小的，或者比最后一个大的均为不可见
            int firstVisiblePosition = lv.getFirstVisiblePosition();
            int lastVisiblePosition = lv.getLastVisiblePosition();
            if (data.headIndex <= firstVisiblePosition || data.headIndex >= lastVisiblePosition) {
                lv.setSelection(data.headIndex);// 可见处理
            }
        }


    }


    private ArrayList<GoodsInfo> datas = new ArrayList<>();
    private ArrayList<GoodsTypeInfo> headDatas;

    public void success(ArrayList<GoodsTypeInfo> goodsTypeInfos) {
        // 安装数据结构处理goodsInfo容器
        headDatas = goodsTypeInfos;

        for (int hi = 0; hi < headDatas.size(); hi++) {

            GoodsTypeInfo head = headDatas.get(hi);


            // 普通条目
            for (int di = 0; di < head.list.size(); di++) {
                GoodsInfo data = head.list.get(di);
                data.headId = head.id;
                data.headIndex = hi;

                if (di == 0)
                    head.groupFirstIndex = datas.size();
                datas.add(data);
            }
        }

        headAdapter = new HeadAdapter(headDatas);
        lv.setAdapter(headAdapter);

        groupAdapter = new StickyListAdapter(headDatas, datas);
        shl.setAdapter(groupAdapter);


        // 监听事件设置
        lv.setOnItemClickListener(this);
        shl.setOnScrollListener(this);
    }

    @OnClick(R.id.cart)
    public void onClick() {
        // 显示购物车
        Intent intent = new Intent(this.getContext(), CartActivity.class);
        this.getContext().startActivity(intent);
    }
}
