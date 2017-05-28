package com.itheima.takeout.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.takeout.MyApplication;
import com.itheima.takeout.R;
import com.itheima.takeout.model.dao.bean.AddressBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 地址列表展示
 */
public class ReceiptAddressActivity extends BaseActivity {

    // 功能描述
    // 1.为rvReceiptAddress添加数据
    // 2.新增地址入口

    // 获取地址数据：
    // 获取网络地址，获取本地地址
    // 如果网络地址获取到后，存储到本地

    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.rv_receipt_address)
    RecyclerView rvReceiptAddress;
    @InjectView(R.id.tv_add_address)
    TextView tvAddAddress;

    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_address);
        ButterKnife.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressPresenter.getData();
    }

    @OnClick({R.id.ib_back, R.id.tv_add_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                break;
            case R.id.tv_add_address:
                Intent intent = new Intent(MyApplication.getContext(), EditReceiptAddressActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyApplication.getContext().startActivity(intent);
                break;
        }
    }

    public void success(Object o) {
        Log.i("Test",o.toString());

        List<AddressBean> been= (List<AddressBean>) o;
        if(been.size()>0){
            // 填充RecyclerView
            rvReceiptAddress.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            adapter=new MyAdapter(been);
            rvReceiptAddress.setAdapter(adapter);
        }else{
            Intent intent = new Intent(MyApplication.getContext(), EditReceiptAddressActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getContext().startActivity(intent);
            Toast.makeText(this, "需要添加地址", Toast.LENGTH_SHORT).show();
        }
    }

    class MyAdapter extends RecyclerView.Adapter {
        private List<AddressBean> been;

        public MyAdapter(List<AddressBean> been) {
            this.been = been;
        }

        public void setBeen(List<AddressBean> been) {
            this.been = been;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_receipt_address, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            AddressBean data = been.get(position);

            viewHolder.setData(data);
        }


        private int getlabelIndex(String label) {
            int index = 0;
            for (int i = 0; i < addressLabels.length; i++) {
                if (label.equals(addressLabels[i])) {
                    index = i;
                    break;
                }
            }
            return index;
        }

        String[] addressLabels = new String[]{"家", "公司", "学校"};
        int[] bgLabels = new int[]{
                Color.parseColor("#fc7251"),//家  橙色
                Color.parseColor("#468ade"),//公司 蓝色
                Color.parseColor("#02c14b"),//学校   绿色

        };

        @Override
        public int getItemCount() {
            return been.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
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
            @InjectView(R.id.iv_edit)
            ImageView ivEdit;
            private AddressBean data;

            ViewHolder(View view) {
                super(view);
                ButterKnife.inject(this, view);
            }

            @OnClick(R.id.iv_edit)
            public void onClick(View view) {

                Intent intent = new Intent(MyApplication.getContext(), EditReceiptAddressActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", data._id);// 地址id
                MyApplication.getContext().startActivity(intent);
            }


            public void setData(AddressBean data) {
                this.data = data;

                tvName.setText(data.name);
                tvSex.setText(data.sex);

                tvPhone.setText(data.phone);


                if (!TextUtils.isEmpty(data.label)) {
                    tvLabel.setVisibility(View.VISIBLE);
                    int index = getlabelIndex(data.label);
                    tvLabel.setText(addressLabels[index]);
                    tvLabel.setBackgroundColor(bgLabels[index]);
                } else {
                    tvLabel.setVisibility(View.GONE);
                }

                tvAddress.setText(data.receiptAddress + data.detailAddress);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 将数据返回给结算中心，返回信息为选中地址的标识
//                        ViewHolder.this.data._id;
                        Intent intent = new Intent();
                        intent.putExtra("id",ViewHolder.this.data._id);
                        ReceiptAddressActivity.this.setResult(200,intent);
                        ReceiptAddressActivity.this.finish();
                    }
                });
            }
        }
    }
}
