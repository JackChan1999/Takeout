package com.itheima.takeout.ui.adapter;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.takeout.App;
import com.itheima.takeout.R;
import com.itheima.takeout.model.net.bean.GoodsTypeInfo;

import java.util.ArrayList;

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
 * des ：商品信息类别列表适配器
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/30 13:52
 * updateDes：${TODO}
 * ============================================================
 */

public class HeadAdapter extends BaseAdapter {
    private ArrayList<GoodsTypeInfo> headDataSet;
    private int selectedHeadIndex;

    public HeadAdapter(ArrayList<GoodsTypeInfo> headDataSet) {
        this.headDataSet = headDataSet;
    }


    @Override
    public int getCount() {
        return headDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return headDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GoodsTypeInfo data = headDataSet.get(position);
        HeadViewHolder holder;
        if (convertView == null) {
            convertView = new TextView(parent.getContext());
            holder = new HeadViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeadViewHolder) convertView.getTag();
        }
        holder.setData(data);

        if (position == selectedHeadIndex)
            holder.itemView.setBackgroundColor(Color.WHITE);


        return convertView;
    }

    public void setSelectedPositon(int index) {
        selectedHeadIndex = index;
        notifyDataSetChanged();
    }

    private class HeadViewHolder {

        private View itemView;
        private GoodsTypeInfo data;

        public HeadViewHolder(View itemView) {
            this.itemView = itemView;
        }

        public void setData(GoodsTypeInfo data) {
            this.data = data;
            ((TextView) itemView).setText(data.name);
            ((TextView) itemView).setTextColor(Color.BLACK);
            ((TextView) itemView).setBackgroundColor(App.getContext().getResources().getColor(R.color.colorItemBg));
            ((TextView) itemView).setTextSize(12);

            int h = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, itemView.getResources().getDisplayMetrics())+0.5f);

            ((TextView) itemView).setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h));
            ((TextView) itemView).setGravity(Gravity.CENTER);
        }
    }
}