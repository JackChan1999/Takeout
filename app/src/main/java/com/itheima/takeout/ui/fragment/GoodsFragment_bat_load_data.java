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

import com.itheima.takeout.MyApplication;
import com.itheima.takeout.R;
import com.itheima.takeout.dagger.conponent.fragment.DaggerGoodsFragmentConponent;
import com.itheima.takeout.dagger.module.fragment.GoodsFragmentModule;
import com.itheima.takeout.model.net.bean.GoodsInfo;
import com.itheima.takeout.model.net.bean.GoodsTypeInfo;
import com.itheima.takeout.presenter.fragment.GoodsFragmentPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


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
public class GoodsFragment_bat_load_data extends BaseFragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    @InjectView(R.id.shl)
    StickyListHeadersListView shl;
    @InjectView(R.id.lv)
    ListView lv;
    private MyGroupAdapter groupAdapter;
    private MyHeadAdapter headAdapter;

    @Inject
    GoodsFragmentPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        DaggerGoodsFragmentConponent.builder().goodsFragmentModule(new GoodsFragmentModule(this)).build().in(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getData(getArguments().getLong("seller_id"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 在头容器中点击某个条目的时候，让该组信息在条目容器中置顶
        // 1.高亮点击条目
        headAdapter.setSelectedPositon(position);
        GoodsTypeInfo head = headDatas.get(position);
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

            GoodsInfo data = datas.get(firstVisibleItem);
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


    private ArrayList<GoodsInfo> datas = new ArrayList<>();
    private ArrayList<GoodsTypeInfo> headDatas;

    public void success(ArrayList<GoodsTypeInfo> goodsTypeInfos) {
        // 安装数据结构处理goodsInfo容器
        headDatas=goodsTypeInfos;

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

        headAdapter = new MyHeadAdapter();
        lv.setAdapter(headAdapter);

        groupAdapter = new MyGroupAdapter();
        shl.setAdapter(groupAdapter);


        // 监听事件设置
        lv.setOnItemClickListener(this);
        shl.setOnScrollListener(this);
    }




    /**
     * 分组的适配器处理
     * BaseAdapter处理普通条目
     * StickyListHeadersAdapter分组头信息
     */
    class MyGroupAdapter extends BaseAdapter implements StickyListHeadersAdapter {
        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            GoodsInfo data = datas.get(position);
            // 头所在集合下标
            GoodsTypeInfo head = headDatas.get(data.headIndex);

            TextView tv = new TextView(MyApplication.getContext());
            tv.setText(head.name);
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
            TextView tv = new TextView(MyApplication.getContext());
            tv.setText(datas.get(position).name);
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
            TextView tv = new TextView(MyApplication.getContext());
            tv.setText(headDatas.get(position).name);
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
