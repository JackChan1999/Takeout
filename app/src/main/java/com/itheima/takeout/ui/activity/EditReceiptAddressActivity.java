package com.itheima.takeout.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.takeout.App;
import com.itheima.takeout.R;
import com.itheima.takeout.model.dao.bean.AddressBean;

import butterknife.ButterKnife;
import butterknife.BindView;
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
 * des ：地址编辑
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/30 13:52
 * updateDes：${TODO}
 * ============================================================
 */
public class EditReceiptAddressActivity extends BaseActivity {
    /**
     * 工作列表：
     * 1.添加地址
     * 2.删除地址
     * 3.修改地址
     * 4.输入信息校验
     */


    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_delete_address)
    ImageButton ibDeleteAddress;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_women)
    RadioButton rbWomen;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.ib_delete_phone)
    ImageButton ibDeletePhone;
    @BindView(R.id.tv_receipt_address)
    TextView tvReceiptAddress;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.ib_select_label)
    ImageView ibSelectLabel;
    @BindView(R.id.bt_ok)
    Button btOk;
    private int id;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_receipt_address);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        if (!TextUtils.isEmpty(App.phone)) {
            etPhone.setText(App.phone);
            ibDeletePhone.setVisibility(View.VISIBLE);
        }

        // 是否有地址的编号传递到编辑界面
        // 如果有，地址的修改或删除
        // 如果没有，添加地址
        id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            tvTitle.setText("修改地址");
            ibDeleteAddress.setVisibility(View.VISIBLE);

            // 需要将本地地址信息查询出来，将值设置到界面上
            addressPresenter.findById(id);

        } else {
            tvTitle.setText("新增地址");
            ibDeleteAddress.setVisibility(View.INVISIBLE);
        }

    }

    @OnClick({R.id.ib_back, R.id.ib_delete_address, R.id.ib_delete_phone, R.id.bt_ok, R.id.ib_select_label,R.id.tv_receipt_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_receipt_address:
                Intent intent=new Intent(this,SelectLocationActivity.class);
                startActivityForResult(intent,200);
                break;

            case R.id.ib_back:
                break;
            case R.id.ib_delete_address:
                showDeleteAlert();
                break;
            case R.id.ib_delete_phone:
                break;
            case R.id.ib_select_label:
                // 选择地址标签
                showLabelAlert();
                break;
            case R.id.bt_ok:
                if (checkReceiptAddressInfo()) {

                    //获取界面数据
                    String name = etName.getText().toString().trim();
                    String sex = "";
                    int checkedRadioButtonId = rgSex.getCheckedRadioButtonId();
                    switch (checkedRadioButtonId) {
                        case R.id.rb_man:
                            sex = "先生";
                            break;
                        case R.id.rb_women:
                            sex = "女士";
                            break;
                    }
                    String phone = etPhone.getText().toString().trim();
                    String receiptAddress = tvReceiptAddress.getText().toString().trim();
                    String detailAddress = etDetailAddress.getText().toString().trim();
                    String label = tvLabel.getText().toString();

                    if (id != -1) {
                        addressPresenter.update(id,name, sex, phone, receiptAddress, detailAddress, label,longitude,latitude);
                    }else {
                        addressPresenter.create(name, sex, phone, receiptAddress, detailAddress, label,longitude,latitude);
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        title
//                snippet
//        intent.putExtra("latitude",point.getLatitude());
//        intent.putExtra("longitude", point.getLongitude());


        if(resultCode==200){
            String title = data.getStringExtra("title");
            String snippet = data.getStringExtra("snippet");

            tvReceiptAddress.setText(title+"\r\n"+snippet);

            latitude = data.getDoubleExtra("latitude",0);
            longitude = data.getDoubleExtra("longitude",0);
        }

    }

    /**
     * 提示用户是否删除
     */
    private void showDeleteAlert() {
        new AlertDialog.Builder(this)
                .setTitle("删除地址")
                .setMessage("确定要删除地址吗？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addressPresenter.delete(id);
                    }
                })
                .create().show();
    }

    private void showLabelAlert() {
        new AlertDialog.Builder(this)
                .setTitle("选择标签")
                .setItems(addressLabels, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which != 0) {
                            tvLabel.setText(addressLabels[which]);
                            tvLabel.setBackgroundColor(bgLabels[which]);
                        }
                    }
                })
                .create().show();
    }

    String[] addressLabels = new String[]{"无", "家", "公司", "学校"};
    int[] bgLabels = new int[]{
            0,
            Color.parseColor("#fc7251"),//家  橙色
            Color.parseColor("#468ade"),//公司 蓝色
            Color.parseColor("#02c14b"),//学校   绿色

    };


    //校验数据
    public boolean checkReceiptAddressInfo() {
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请填写联系人", Toast.LENGTH_SHORT).show();
            return false;
        }
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请填写手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isMobileNO(phone)) {
            Toast.makeText(this, "请填写合法的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        String receiptAddress = tvReceiptAddress.getText().toString().trim();
        if (TextUtils.isEmpty(receiptAddress)) {
            Toast.makeText(this, "请填写收获地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        String address = etDetailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isMobileNO(String phone) {
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return phone.matches(telRegex);
    }

    @Override
    public void success(Object o) {

        // 更新界面
        if (o instanceof AddressBean) {
            AddressBean bean = (AddressBean) o;
            etName.setText(bean.name);
            etName.setSelection(bean.name.length());//让光标在最后

            if (!TextUtils.isEmpty(bean.sex)) {
                if (bean.sex.equals("先生")) {
                    rbMan.setChecked(true);
                } else {
                    rbWomen.setChecked(true);
                }
            }

            etPhone.setText(bean.phone);
            tvReceiptAddress.setText(bean.receiptAddress);
            etDetailAddress.setText(bean.detailAddress);

            if (!TextUtils.isEmpty(bean.label)) {
                int index = getIndexLabel(bean.label);
                tvLabel.setText(addressLabels[index]);
                tvLabel.setBackgroundColor(bgLabels[index]);
            }
        } else {
            finish();
        }
    }

    private int getIndexLabel(String label) {
        int index = 0;
        for (int i = 0; i < addressLabels.length; i++) {
            if (label.equals(addressLabels[i])) {
                index = i;
                break;
            }
        }
        return index;
    }
}
