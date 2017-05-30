package com.itheima.takeout.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.itheima.takeout.R;
import com.itheima.takeout.ui.fragment.GoodsFragment;
import com.itheima.takeout.ui.fragment.RecommendFragment;
import com.itheima.takeout.ui.fragment.SellerFragment;
import com.itheima.takeout.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.BindView;
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
 * 商家详细信息展示
 * 1.处理标题（ToolBar）
 * a.调整状态栏样式：不需要全屏、修改颜色（色调与首页相同）
 * b.添加ToolBar控制颜色与状态栏相同
 * c.标题文字
 * d.返回键（Home）
 * 2.处理TabLayout，展示"商品","评价","商家"
 * 3.ViewPager处理及绑定TabLayout
 * 4.展示商品信息
 * 5.购物车
 * 6.选择商品
 */
public class SellerDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.vp)
    ViewPager vp;


    private String[] titles = new String[]{"商品", "评价", "商家"};
    private MyAdapter adapter;
    private long sellerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        sellerId = intent.getLongExtra("seller_id", -1);
        String name = intent.getStringExtra("name");

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);// Toolbar的相关配置需要在setSupportActionBar之前完成

        setSupportActionBar(toolbar);// 替换ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // TabLayout
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // tabLayout.addTab();添加选项卡

        adapter = new MyAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        tabs.setupWithViewPager(vp);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // 获取到状态栏的高度
        Rect outRect = new Rect();
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        UIUtils.STATUE_BAR_HEIGHT = outRect.top;// 状态栏的高度

    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new GoodsFragment();
                    Bundle arguments = new Bundle();
                    arguments.putLong("seller_id", sellerId);
                    fragment.setArguments(arguments);
                    break;
                case 1:
                    fragment = new RecommendFragment();
                    break;
                case 2:
                    fragment = new SellerFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
