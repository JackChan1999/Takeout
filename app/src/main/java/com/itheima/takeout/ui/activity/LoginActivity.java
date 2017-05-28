package com.itheima.takeout.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.takeout.R;
import com.itheima.takeout.utils.PromptManager;
import com.itheima.takeout.utils.SMSUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * 用户登录界面：
 * 1、引入短信校验相关工具
 * 2、获取验证码
 * 等待时间处理
 * 3、发送验证码
 * 4、把手机号码发送到自己的服务器，做注册或登陆操作
 */
public class LoginActivity extends BaseActivity {

    /*
    App Key：16db05f4edda0
App Secret：f5f8e5c52e11337353979972c1b08cae

     */


    private static final String APPKEY = "16db05f4edda0";
    private static final java.lang.String APPSECRET = "f5f8e5c52e11337353979972c1b08cae";
    private static final int SENDING = -9;
    private static final int RESEND = -8;
    private static final String TAG = "LoginActivity";
    @InjectView(R.id.iv_user_back)
    ImageView ivUserBack;
    @InjectView(R.id.iv_user_password_login)
    TextView ivUserPasswordLogin;
    @InjectView(R.id.et_user_phone)
    EditText etUserPhone;
    @InjectView(R.id.tv_user_code)
    TextView tvUserCode;
    @InjectView(R.id.et_user_code)
    EditText etUserCode;
    @InjectView(R.id.login)
    TextView login;
    private java.lang.String phone;
    private int i = 60;//倒计时

    /**
     * 1、	权限校验: SMSUtil.checkPermission(this);
     * 2、	初始化工具: SMSSDK.initSDK(this, APPKEY, APPSECRET, true);
     * 3、	注册事件监听：SMSSDK.registerEventHandler(eventHandler);
     * 4、	获取验证码：SMSSDK.getVerificationCode("86", phone);监听事件触发。
     * 5、	发送验证码：SMSSDK.submitVerificationCode("86", phone, code.trim());监听事件触发。
     * 6、	注销监听。
     */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        //权限校验:
        SMSUtil.checkPermission(this);
        //初始化工具:
        SMSSDK.initSDK(this, APPKEY, APPSECRET, true);
    }


    @OnClick({R.id.tv_user_code, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_user_code:
                // 校验用户输入信息
                phone = etUserPhone.getText().toString();
                boolean judgePhoneNums = SMSUtil.judgePhoneNums(this, phone);
                if (!judgePhoneNums) {
                    return;
                }


                // 获取验证码
                SMSSDK.getVerificationCode("86", phone);

                // 修改发送按钮信息及状态
                tvUserCode.setEnabled(false);
                tvUserCode.setText("重新发送（" + i + "）");
                new Thread() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(SENDING);
                            if (i <= 0)
                                break;
                            SystemClock.sleep(999);
                        }
                        handler.sendEmptyMessage(RESEND);
                    }
                }.start();

                break;
            case R.id.login:
                // 发送验证码
//                String code = etUserCode.getText().toString();
//                if (!TextUtils.isEmpty(code)) {
//                    SMSSDK.submitVerificationCode("86", phone, code);
//                    PromptManager.showProgressDialog(this);
//                }

                testData();

                break;
        }
    }

    private void testData() {
        // 测试，当通过短信验证之后。

        presenter.getData(etUserPhone.getText().toString());
    }


    private EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg=new Message();
            msg.arg1=event;
            msg.arg2=result;
            msg.obj=data;
            handler.sendMessage(msg);

        }

    };

    @Override
    protected void onResume() {
        super.onResume();
        //注册事件监听：
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SMSSDK.unregisterAllEventHandler();
    }



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SENDING) {
                tvUserCode.setText("重新发送（" + i + "）");
            } else if (msg.what == RESEND) {
                tvUserCode.setText("获取验证码");
                tvUserCode.setEnabled(true);
            }else{
                int event=msg.arg1;
                int result=msg.arg2;
                Object data=msg.obj;
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功(服务器短信验证通过)
                        Log.i(TAG, "验证通过了");
                        // TODO 将用户输入的电话号码发送到服务器。
                        // 服务器工作，判断是否有该用户记录，如果有，将用户信息返还给手机端，如果没有创建一条用户记录。
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功（服务器发送验证码成功）
                        Log.i(TAG, "服务器已经发送验证码");
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    PromptManager.closeProgressDialog();
                }
            }
        }
    };

    public void changUI() {
        PromptManager.closeProgressDialog();
        finish();
    }

    /**
     * 处理异常提示
     * @param msg
     */
    public void failed(String msg) {
    }

    public void success() {
        // 界面跳转
        finish();
    }
}