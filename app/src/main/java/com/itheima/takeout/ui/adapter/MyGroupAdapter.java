package com.itheima.takeout.ui.adapter;

import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.takeout.MyApplication;
import com.itheima.takeout.R;
import com.itheima.takeout.model.net.bean.GoodsInfo;
import com.itheima.takeout.model.net.bean.GoodsTypeInfo;
import com.itheima.takeout.ui.ShoppingCartManager;
import com.itheima.takeout.utils.AnimationUtils;
import com.itheima.takeout.utils.NumberFormatUtils;
import com.itheima.takeout.utils.UiUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import static android.R.attr.animation;
import static com.itheima.takeout.utils.UiUtils.getContainder;

/**
 * 分组容器适配
 */

public class MyGroupAdapter extends BaseAdapter implements StickyListHeadersAdapter {


    private final ArrayList<GoodsTypeInfo> headDataSet;
    private final ArrayList<GoodsInfo> itemDataSet;

    public MyGroupAdapter(ArrayList<GoodsTypeInfo> headDataSet, ArrayList<GoodsInfo> itemDataSet) {
        this.headDataSet = headDataSet;
        this.itemDataSet = itemDataSet;
    }

    //////////////////////////////////头管理/////////////////////////////////////////////
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        // 向下滚动：头数据加载的是每组的第一条
        // 向上滚动：头数据加载的是每组的最后一条

        TextView head = new TextView(parent.getContext());
        GoodsTypeInfo headData = headDataSet.get(itemDataSet.get(position).headIndex);
        head.setText(headData.name);
        head.setBackgroundColor(MyApplication.getContext().getResources().getColor(R.color.colorItemBg));
        return head;
    }

    @Override
    public long getHeaderId(int position) {
        return itemDataSet.get(position).headId;
    }

    //////////////////////////////////普通条目管理/////////////////////////////////////////////
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsInfo data = itemDataSet.get(position);
        ItemViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(MyApplication.getContext(), R.layout.item_goods, null);
            holder = new ItemViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemViewHolder) convertView.getTag();
        }

        holder.setData(data);
        return convertView;
    }

    @Override
    public int getCount() {
        return itemDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return itemDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ItemViewHolder {
        View itemView;
        private GoodsInfo data;

        @InjectView(R.id.iv_icon)
        ImageView ivIcon;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_zucheng)
        TextView tvZucheng;
        @InjectView(R.id.tv_yueshaoshounum)
        TextView tvYueshaoshounum;
        @InjectView(R.id.tv_newprice)
        TextView tvNewprice;
        @InjectView(R.id.tv_oldprice)
        TextView tvOldprice;
        @InjectView(R.id.ib_minus)
        ImageButton ibMinus;
        @InjectView(R.id.tv_money)
        TextView tvCount;
        @InjectView(R.id.ib_add)
        ImageButton ibAdd;
        private FrameLayout container;
        private TextView count;


        public ItemViewHolder(View itemView) {
            this.itemView = itemView;
            ButterKnife.inject(this, this.itemView);
        }

        @OnClick({R.id.ib_minus, R.id.ib_add})
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_minus:
                    minusHander(v);
                    break;
                case R.id.ib_add:
                    addHandler(v);
                    break;
            }
        }

        /**
         * 处理减少的操作
         *
         * @param v
         */
        private void minusHander(View v) {
            Integer num = ShoppingCartManager.getInstance().minusGoods(data);
            if (num == 0) {
                // 动画处理
                AnimationSet animation = AnimationUtils.getHideMinusAnimation();
                ibMinus.startAnimation(animation);
                tvCount.startAnimation(animation);

                animation.setAnimationListener(new AnimationUtils.AnimationListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ibMinus.setVisibility(View.GONE);
                        tvCount.setVisibility(View.GONE);
                    }
                });
            }


            tvCount.setText(num.toString());



            // 处理购物车气泡的显示
            Integer totalNum=ShoppingCartManager.getInstance().getTotalNum();
            if(totalNum==0){
                count.setVisibility(View.INVISIBLE);
            }
            count.setText(totalNum.toString());
        }

        /**
         * 添加动作处理
         *
         * @param v
         */
        private void addHandler(View v) {
            Integer num = ShoppingCartManager.getInstance().addGoods(data);
            if (num == 1) {// 第一次动画执行

                // 动画操作:－的ImageView和数量TextView
                AnimationSet showMinusAnimation = AnimationUtils.getShowMinusAnimation();
                tvCount.startAnimation(showMinusAnimation);
                ibMinus.startAnimation(showMinusAnimation);

                tvCount.setVisibility(View.VISIBLE);
                ibMinus.setVisibility(View.VISIBLE);
            }

            tvCount.setText(num.toString());




            // 处理飞入到购物车的动画
            flyToShoppingCart(v);

            // 修改气泡提示
            if(count==null) {
                count = (TextView) container.findViewById(R.id.fragment_goods_tv_count);
            }

            if(num>0){
                count.setVisibility(View.VISIBLE);
            }
            Integer totalNum=ShoppingCartManager.getInstance().getTotalNum();
            count.setText(totalNum.toString());
        }

        private void flyToShoppingCart(View v) {
            // 1、获取该控件所在的位置
            int[] location=new int[2];
            v.getLocationOnScreen(location);// 获取到控件所在整个屏幕的位置

            // 2、获取目标位置（购物车图片所在位置）
            int [] targetLocation=new int[2];
            // 将Activity加载控件容器获取到
            if(container==null) {
                container = (FrameLayout) UiUtils.getContainder(v, R.id.seller_detail_container);
            }
            container.findViewById(R.id.cart).getLocationOnScreen(targetLocation);

            // getLocationOnScreen获取控件所在屏幕中的位置，需要在y轴方向将状态栏的高度减掉
            location[1]-=UiUtils.STATUE_BAR_HEIGHT;
            targetLocation[1]-=UiUtils.STATUE_BAR_HEIGHT;

            // 创建一个控件，放到“+”按钮地方，执行动画
            final ImageView iv=getImageView(location,v);
            Animation animation = AnimationUtils.getAddAnimation(targetLocation, location);
            iv.startAnimation(animation);
            animation.setAnimationListener(new AnimationUtils.AnimationListenerAdapter(){
                @Override
                public void onAnimationEnd(Animation animation) {
                    container.removeView(iv);
                }
            });
        }

        private ImageView getImageView(int[] location, View v){
            ImageView iv=new ImageButton(MyApplication.getContext());
            iv.setX(location[0]);
            iv.setY(location[1]);
            iv.setBackgroundResource(R.mipmap.food_button_add);


            // 将该控件放到何处（条目），存在问题，如果将控件放到条目中的话，在点击时执行动画，只能在条目中看到控制动画执行，超出条目的范围控件就不可见
//            ((ViewGroup)itemView).addView(iv,v.getWidth(),v.getHeight());
            container.addView(iv,v.getWidth(),v.getHeight());

            return iv;
        }

        public void setData(GoodsInfo data) {
            this.data = data;

            //图片
            Picasso.with(MyApplication.getContext()).load(data.icon).into(ivIcon);
            tvName.setText(data.name);
            if (TextUtils.isEmpty(data.form)) {
                tvZucheng.setVisibility(View.GONE);
            } else {
                tvZucheng.setVisibility(View.VISIBLE);
                tvZucheng.setText(data.form);
            }
            tvYueshaoshounum.setText("月销售" + data.monthSaleNum + "份");
            tvNewprice.setText(NumberFormatUtils.formatDigits(data.newPrice));
            if (data.oldPrice == 0) {
                tvOldprice.setVisibility(View.GONE);
            } else {
                tvOldprice.setVisibility(View.VISIBLE);
                tvOldprice.setText(NumberFormatUtils.formatDigits(data.oldPrice));
                //TextView出现中间的线
                tvOldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }

            // 判断购物重是否有当前条目的商品，如果有需要获取到商品的数据量
            Integer num = ShoppingCartManager.getInstance().getGoodsIdNum(data.id);
            if(num>0){
                ibMinus.setVisibility(View.VISIBLE);
                tvCount.setVisibility(View.VISIBLE);
                tvCount.setText(num.toString());
            }else {
                ibMinus.setVisibility(View.INVISIBLE);
                tvCount.setVisibility(View.INVISIBLE);
            }
        }
    }
}