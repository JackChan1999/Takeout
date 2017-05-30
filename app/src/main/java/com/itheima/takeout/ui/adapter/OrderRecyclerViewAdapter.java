package com.itheima.takeout.ui.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.takeout.R;
import com.itheima.takeout.model.net.bean.GoodsInfo;
import com.itheima.takeout.model.net.bean.Order;
import com.itheima.takeout.ui.activity.OrderDetailActivity;
import com.itheima.takeout.ui.observer.OrderObserver;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
public class OrderRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<Order> orders;
    
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_order_item, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        System.out.println("holder = [" + holder + "], position = [" + position + "]");
        final Order order = orders.get(position);

        ((ItemViewHolder) holder).setOrder(order);
        ((ItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OrderDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("orderId",order.id);
                bundle.putString("type",order.type);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orders != null && orders.size() > 0) {
            return orders.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private Order order;

        @BindView(R.id.tv_order_item_seller_name)
        TextView tvOrderItemSellerName;
        @BindView(R.id.iv_order_item_seller_logo)
        ImageView ivOrderItemSellerLogo;
        @BindView(R.id.tv_order_item_type)
        TextView tvOrderItemType;
        @BindView(R.id.tv_order_item_time)
        TextView tvOrderItemTime;
        @BindView(R.id.tv_order_item_foods)
        TextView tvOrderItemFoods;
        @BindView(R.id.tv_order_item_money)
        TextView tvOrderItemMoney;

        @BindView(R.id.tv_order_item_multi_function)
        TextView tvOrderItemMultiFunction;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOrder(Order order) {
            this.order = order;
            tvOrderItemSellerName.setText(order.seller.name);
            Picasso.with(itemView.getContext()).load(order.seller.pic).into(ivOrderItemSellerLogo);

            // 设置订单类型
            tvOrderItemType.setText(getOrderTypeInfo(order.type));
            tvOrderItemTime.setText(order.detail.time);


            // 商品提示信息：红烧肉等几件商品
            // 获取到最贵的商品，然后统计数量

            List<GoodsInfo> goodsInfos = order.goodsInfos;

            float price = 0f;
            String name = "";
            float sumPrice = 0f;
            for (GoodsInfo item : goodsInfos) {
                float newPrice = item.newPrice;
                if (price < newPrice) {
                    price = newPrice;
                    name = item.name;
                }
                sumPrice += newPrice;
            }

            tvOrderItemFoods.setText(name + "等" + goodsInfos.size() + "件商品");
            tvOrderItemMoney.setText("￥" + sumPrice);

            tvOrderItemMultiFunction.setText("再来一单");
        }

        private String getOrderTypeInfo(String type) {
            /**
             * 订单状态
             * 1 未支付 2 已提交订单 3 商家接单  4 配送中,等待送达 5已送达 6 取消的订单
             */
//            public static final String ORDERTYPE_UNPAYMENT = "10";
//            public static final String ORDERTYPE_SUBMIT = "20";
//            public static final String ORDERTYPE_RECEIVEORDER = "30";
//            public static final String ORDERTYPE_DISTRIBUTION = "40";
//            public static final String ORDERTYPE_SERVED = "50";
//            public static final String ORDERTYPE_CANCELLEDORDER = "60";

            String typeInfo = "";
            switch (type) {
                case OrderObserver.ORDERTYPE_UNPAYMENT:
                    typeInfo = "未支付";
                    break;
                case OrderObserver.ORDERTYPE_SUBMIT:
                    typeInfo = "已提交订单";
                    break;
                case OrderObserver.ORDERTYPE_RECEIVEORDER:
                    typeInfo = "商家接单";
                    break;
                case OrderObserver.ORDERTYPE_DISTRIBUTION:
                    typeInfo = "配送中";
                    break;
                case OrderObserver.ORDERTYPE_SERVED:
                    typeInfo = "已送达";
                    break;
                case OrderObserver.ORDERTYPE_CANCELLEDORDER:
                    typeInfo = "取消的订单";
                    break;
            }
            return typeInfo;
        }
    }
}
