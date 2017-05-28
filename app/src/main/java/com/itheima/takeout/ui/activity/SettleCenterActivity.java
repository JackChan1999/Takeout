package com.itheima.takeout.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.itheima.takeout.MyApplication;
import com.itheima.takeout.R;
import com.itheima.takeout.model.dao.bean.AddressBean;
import com.itheima.takeout.model.net.bean.GoodsInfo;
import com.itheima.takeout.ui.ShoppingCartManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 结算中心
 */
public class SettleCenterActivity extends BaseActivity {
    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_sex)
    TextView tvSex;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.tv_label)
    TextView tvLabel;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.ll_selected_address_container)
    LinearLayout llSelectedAddressContainer;
    @InjectView(R.id.tv_select_address)
    TextView tvSelectAddress;
    @InjectView(R.id.rl_location)
    RelativeLayout rlLocation;
    @InjectView(R.id.iv_logo)
    ImageView ivLogo;
    @InjectView(R.id.tv_seller_name)
    TextView tvSellerName;
    @InjectView(R.id.ll_select_goods)
    LinearLayout llSelectGoods;
    @InjectView(R.id.tv_send_price)
    TextView tvSendPrice;
    @InjectView(R.id.tv_count_price)
    TextView tvCountPrice;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;

    // 功能列表
    // 1.布局
    // 2.地址管理入口
    // 3.设置购物车数据


    // TODO 地址工作：
    // 1.到数据库中获取地址列表的数据
    // 2.展示默认地址（默认地址不是用户设置）   应用在首页会有定位（经纬度信息）
    // 在地址中会存放经纬度信息，依据获取到的用户位置信息和地址列表中的位置信息进行比对
    // 计算两个点之间的距离：一旦距离小于200米，将当前的比对地址作为默认地址
    // 3.用户手动设置地址


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_center);
        ButterKnife.inject(this);


        setData();
    }

    private void setData() {
        if(MyApplication.LOCATION!=null) {
            // 查询地址列表
            addressPresenter.findAllByUserId(MyApplication.USERID);
        }


        // 设置商家
        // 设置购买商品
        // 配送费设置
        // 支付总额设置

//        ShoppingCartManager.getInstance().url
//        ShoppingCartManager.getInstance().name

        ivLogo.setImageResource(R.drawable.item_logo);
        tvSellerName.setText(ShoppingCartManager.getInstance().name);

        CopyOnWriteArrayList<GoodsInfo> goodsInfos = ShoppingCartManager.getInstance().goodsInfos;
        for (GoodsInfo item : goodsInfos) {
            View v = View.inflate(this, R.layout.item_settle_center_goods, null);
            // 数据设置
            ((TextView) v.findViewById(R.id.tv_name)).setText(item.name);
            ((TextView) v.findViewById(R.id.tv_count)).setText("X" + item.count);
            ((TextView) v.findViewById(R.id.tv_price)).setText("￥" + item.newPrice);
            // android:layout_width="match_parent" android:layout_height="30dp"
            int h = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
            llSelectGoods.addView(v, ViewGroup.LayoutParams.MATCH_PARENT, h);
        }


        tvSendPrice.setText("￥" + ShoppingCartManager.getInstance().sendPrice);

        float money = ShoppingCartManager.getInstance().getMoney() / 100.0f + ShoppingCartManager.getInstance().sendPrice;

        tvCountPrice.setText("待支付￥" + money);
    }

    @OnClick({R.id.ib_back, R.id.rl_location, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                break;
            case R.id.rl_location:
                // 地址管理入口
                Intent intent = new Intent(this, ReceiptAddressActivity.class);
                startActivityForResult(intent, 200);
                break;
            case R.id.tv_submit:
                // 用户输入校验：地址（是否选择了配送地址）
                if (addressId != -1) {
                    // 提交订单数据到服务器，服务器会向订单数据库中插入一条记录，生成对应的订单编号，该编号会回复给手机端，手机端收到该编号后去订单支付界面
                    orderPresenter.create(MyApplication.USERID, addressId, 1);
                } else {
                    Toast.makeText(this, "请选择配送地址", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private int addressId = -1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            int id = data.getIntExtra("id", -1);
            if (id != -1) {
                addressId = id;
                addressPresenter.findById(id);
            }
        }
    }

    @Override
    public void success(Object o) {
        if (o instanceof AddressBean) {
            AddressBean bean = (AddressBean) o;
            tvSelectAddress.setVisibility(View.GONE);
            llSelectedAddressContainer.setVisibility(View.VISIBLE);

            tvName.setText(bean.name);

            // 其他的TextView不进行设置
        }

        if (o instanceof String) {
            // TODO 服务器生成的订单编号已经获取到了，手机端收到该编号后去订单支付界面
            String orderId = o.toString();
            Intent intent = new Intent(this, OnlinePayActivity.class);
            intent.putExtra("orderId", orderId);
            startActivity(intent);
        }

        if (o instanceof List) {
            // 判断定位点与地址列表中那个记录的距离近（小于500米），将该地址信息作为默认地址
            List<AddressBean> been = (List<AddressBean>) o;
            for (AddressBean item : been) {
                LatLonPoint point = new LatLonPoint(item.latitude, item.longitude);
                double distance = getDistance(MyApplication.LOCATION, point);
                if (distance < 500) {
                    // 该条目为默认地址
                    // 修改界面
                    tvSelectAddress.setVisibility(View.GONE);
                    llSelectedAddressContainer.setVisibility(View.VISIBLE);

                    tvName.setText(item.name);
                }
            }
        }
    }

    /*
    * 计算两点之间距离
    *
    * @param start
    *
    * @param end
    *
    * @return 米
    */
    public double getDistance(LatLonPoint start, LatLonPoint end) {

        double lon1 = (Math.PI / 180) * start.getLongitude();
        double lon2 = (Math.PI / 180) * end.getLongitude();
        double lat1 = (Math.PI / 180) * start.getLatitude();
        double lat2 = (Math.PI / 180) * end.getLatitude();

        // 地球半径
        double R = 6371;

        // 两点间距离 km，如果想要米的话，结果*1000就可以了
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;

        return d * 1000;
    }
}
