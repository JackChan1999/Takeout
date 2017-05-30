package com.itheima.takeout.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.takeout.App;
import com.itheima.takeout.R;
import com.itheima.takeout.model.net.bean.GoodsInfo;
import com.itheima.takeout.ui.ShoppingCartManager;
import com.itheima.takeout.ui.views.RecycleViewDivider;
import com.itheima.takeout.utils.NumberFormatUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
 * des ：购物车界面
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/30 13:52
 * updateDes：${TODO}
 * ============================================================
 */
public class CartActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.cart_rv)
    RecyclerView cartRv;
    private MyCartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        toolbar.setTitle("购物车");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter=new MyCartAdapter();
        cartRv.setAdapter(adapter);
        cartRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        // 设置分割线
        cartRv.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,1,0XE3E0DC));


        tvMoney.setText(NumberFormatUtils.formatDigits(ShoppingCartManager.getInstance().getMoney()/100.0));
    }



    @OnClick(R.id.button)
    public void onClick() {
        // 登陆入口一
        // 判断是否登陆了
        // 如果登陆了，去订单生成界面
        // 没有登陆，去用户登陆界面

        Intent intent =null;
        if(App.USERID!=0){
            // 如果登陆了，去订单生成界面
            intent = new Intent(this, SettleCenterActivity.class);
        }else{
            // 没有登陆，去用户登陆界面
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);

    }

    class MyCartAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(View.inflate(App.getContext(), R.layout.item_cart, null));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder) holder).setData(ShoppingCartManager.getInstance().goodsInfos.get(position));
        }

        @Override
        public int getItemCount() {
            return ShoppingCartManager.getInstance().goodsInfos.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.item_iv)
            ImageView itemIv;
            @BindView(R.id.item_tv_name)
            TextView itemTvName;
            @BindView(R.id.item_tv_price)
            TextView itemTvPrice;
            @BindView(R.id.item_tv_num)
            TextView itemTvNum;

            private GoodsInfo data;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, this.itemView);
            }

            public void setData(GoodsInfo data) {
                this.data = data;
                Picasso.with(App.getContext()).load(data.icon).into(itemIv);
                itemTvName.setText(data.name);
                itemTvPrice.setText(NumberFormatUtils.formatDigits(data.newPrice));
                itemTvNum.setText(data.count+"");
            }
        }



    }
}
