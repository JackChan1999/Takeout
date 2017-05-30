package com.itheima.takeout.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.takeout.App;
import com.itheima.takeout.R;
import com.itheima.takeout.model.net.bean.GoodsTypeInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
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
public class GoodsFragment_bat extends BaseFragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    @BindView(R.id.shl)
    StickyListHeadersListView shl;
    @BindView(R.id.lv)
    ListView lv;

    private Unbinder unbinder;

    private MyGroupAdapter groupAdapter;
    private MyHeadAdapter headAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        testData();
        headAdapter = new MyHeadAdapter();
        lv.setAdapter(headAdapter);

        groupAdapter = new MyGroupAdapter();
        shl.setAdapter(groupAdapter);


        // 监听事件设置
        lv.setOnItemClickListener(this);
        shl.setOnScrollListener(this);
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
        Head head = headDatas.get(position);
        shl.setSelection(head.groupFirstIndex);
        isScroll=false;
    }

    private boolean isScroll=false;
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        System.out.println("scrollState:"+scrollState);
        // 用户在滚动
        isScroll=true;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 用户的滚动
        if(isScroll) {
            System.out.println("firstVisibleItem:" + firstVisibleItem);

            Data data = datas.get(firstVisibleItem);
            System.out.println("data.headIndex:"+data.headIndex);
            // 当前正在置顶显示的头高亮处理
            headAdapter.setSelectedPositon(data.headIndex);

            // 判断头容器对应的条目是否处于可见状态
            // 获取到第一个可见，和最后一个可见的。比第一个小的，或者比最后一个大的均为不可见
            int firstVisiblePosition = lv.getFirstVisiblePosition();
            int lastVisiblePosition = lv.getLastVisiblePosition();
            if(data.headIndex<=firstVisiblePosition||data.headIndex>=lastVisiblePosition){
                lv.setSelection(data.headIndex);// 可见处理
            }
        }


    }

    public void success(List<GoodsTypeInfo> goodsTypeInfos) {

    }

    /**
     * 普通条目
     */
    class Data {
        String info;

        int headId;// 进行分组操作，同组数据该字段值相同

        int headIndex;  // 当前条目对应头数据所在集合的下标
    }

    /**
     * 头条目
     */
    class Head {
        String info;
        // 点击摸个头时，需要知道其分组容器中对应组元素中第一条的下标
        int groupFirstIndex;
    }

    private ArrayList<Data> datas = new ArrayList<>();
    private ArrayList<Head> headDatas = new ArrayList<>();

    private void testData() {
        /**
         * 头
         * 条目
         * 条目
         * 条目
         * 条目
         * 头
         * 条目
         * 条目
         * 条目
         * 条目
         */

        // 分组：0-9为一个分组
        for (int hi = 0; hi < 10; hi++) {
            Head head = new Head();
            head.info = "头：" + hi;
            headDatas.add(head);
        }

        // 普通条目
//        for(int di=0;di<100;di++){
//            Data data=new Data();
//            data.info="普通条目："+di;
//            datas.add(data);
//        }

        for (int hi = 0; hi < headDatas.size(); hi++) {

            Head head = headDatas.get(hi);


            // 普通条目
            for (int di = 0; di < 10; di++) {
                Data data = new Data();
                data.headId = hi;// 任意值
                data.info = "普通条目：第" + hi + "组，条目数" + di;

                data.headIndex = hi;

                if (di == 0)
                    head.groupFirstIndex = datas.size();
                datas.add(data);
            }
        }
    }

    /**
     * 分组的适配器处理
     * BaseAdapter处理普通条目
     * StickyListHeadersAdapter分组头信息
     */
    class MyGroupAdapter extends BaseAdapter implements StickyListHeadersAdapter {
        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            Data data = datas.get(position);
            // 头所在集合下标
            Head head = headDatas.get(data.headIndex);

            TextView tv = new TextView(App.getContext());
            tv.setText(head.info);
            tv.setBackgroundColor(Color.GRAY);
            return tv;
        }

        @Override
        public long getHeaderId(int position) {
            // 依据position获取普通条目
            // 普通条目中存放了headId

            return datas.get(position).headId;
        }

        //////////////////////////////////普通条目////////////////////////////////////////
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(App.getContext());
            tv.setText(datas.get(position).info);
            tv.setTextColor(Color.GRAY);
            return tv;
        }
    }

    class MyHeadAdapter extends BaseAdapter {
        private int selectedPositon;

        @Override
        public int getCount() {
            return headDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return headDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(App.getContext());
            tv.setText(headDatas.get(position).info);
            tv.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(16);
            tv.setTextColor(Color.BLACK);


            if (position == selectedPositon) {
                tv.setBackgroundColor(Color.WHITE);
            } else {
                tv.setBackgroundColor(Color.GRAY);
            }
            return tv;
        }

        public void setSelectedPositon(int selectedPositon) {
            if(this.selectedPositon==selectedPositon){
                return;
            }

            this.selectedPositon = selectedPositon;
            System.out.println("刷新");
            notifyDataSetChanged();
        }
    }
}
