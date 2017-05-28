package com.itheima.takeout.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.takeout.R;
import com.itheima.takeout.model.net.bean.PaymentInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 在线支付界面
 */
public class OnlinePayActivity extends BaseActivity {

    // 1.获取支付信息展示给用户
    // 2.支付



    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_residualTime)
    TextView tvResidualTime;
    @BindView(R.id.tv_order_name)
    TextView tvOrderName;
    @BindView(R.id.ll_order_detail)
    LinearLayout llOrderDetail;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.ll_pay_type_container)
    LinearLayout llPayTypeContainer;
    @BindView(R.id.bt_confirm_pay)
    Button btConfirmPay;



    private Handler mHandler = new Handler();


    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_pay);

        ButterKnife.bind(this);
        orderId = getIntent().getStringExtra("orderId");
        tvOrderName.setText("第"+orderId+"号订单");
    }

    @Override
    protected void onResume() {
        super.onResume();
        paymentPresenter.getData(orderId);
    }

    @Override
    public void success(Object o) {

        if(o instanceof HashMap){

            HashMap<String, Object> data = (HashMap<String, Object>) o;
            int payDownTime = (int) data.get("payDownTime");

            mHandler.post(new MyResidualTimerTask(payDownTime * 60));
            tvPayMoney.setText("￥" + data.get("money").toString());
            List<PaymentInfo> payments = (List<PaymentInfo>) data.get("paymentInfo");
            addPayment(payments);
        }
    }


    // 处理支付方式的选择：
    // 在支付条目添加时候，获取所有条目的CheckBox，将这些CheckBox引用添加到一个容器，当某个CheckBox被选中的时候需要循环该容器，其他的全部修改为没有选中状态

    ArrayList<CheckBox> cbs=new ArrayList<>();

    private void addPayment(List<PaymentInfo> payments) {
        for ( PaymentInfo item : payments) {
            // 分割线
            View view = new View(this);
            view.setBackgroundColor(0xfd9b9999);
            int h = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()) + .5);
            llPayTypeContainer.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, h);

            view = View.inflate(this, R.layout.item_online_pay, null);
            Picasso.with(this).load(item.url).into((ImageView) view.findViewById(R.id.iv_pay_logo));
            ((TextView) view.findViewById(R.id.tv_pay_name)).setText(item.name);
            CheckBox cb = (CheckBox) view.findViewById(R.id.cb_pay_selected);

            // 如何做到点击某个CheckBox知道起对应的支付信息
            cb.setTag(item.id);
            cbs.add(cb);

            llPayTypeContainer.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        setCheckBoxCheckedListener();

    }

    private  int paymentType=-1;
    private void setCheckBoxCheckedListener() {
        for(CheckBox cb :cbs){
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // 如果集合中的与传递进来的buttonView相等，说明该cb为用户选择的，其他的需要调整选择状态

                    if(isChecked){
                        for(CheckBox item:cbs){
                            if(item!=buttonView){
                                item.setChecked(false);
                            }else{
                                paymentType= (int) item.getTag();
                            }
                        }
                    }
                }
            });
        }
    }

    private class MyResidualTimerTask implements Runnable {

        private int time;

        public MyResidualTimerTask(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            time = time - 1;
            if (time == 0) {
                btConfirmPay.setEnabled(false);
            }
            int m = time / 60;
            int s = time % 60;
            tvResidualTime.setText("支付剩余时间:" + m + "分" + s + "秒");
            mHandler.postDelayed(this, 999);
        }
    }

}
