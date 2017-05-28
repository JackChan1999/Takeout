package com.itheima.mvc;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // 业务代码：
    // 界面业务
    // 业务流程的处理

    private EditText mUsername;
    private EditText mPassword;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        dialog = new ProgressDialog(this);

    }

    /**
     * 按钮点击
     *
     * @param view
     */
    public void login(View view) {
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        boolean checkUserInfo = checkUserInfo(username,password);

        if(checkUserInfo){
            dialog.show();
            final User user=new User();
            user.username=username;
            user.password=password;
            new Thread(){
                @Override
                public void run() {
                    UserLoginNet net=new UserLoginNet();

                    if(net.sendUserLoginInfo(user)){
                        // 登陆成功
                        success();
                    }else{
                        //登陆失败
                        failed();
                    }

                }
            }.start();
        }else{
            Toast.makeText(MainActivity.this, R.string.check_info, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 检验用户输入——界面相关逻辑处理
     * @param username
     * @param password
     * @return
     */
    private boolean checkUserInfo(String username, String password) {
        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            return false;
        }
        return true;
    }

    /**
     * 登陆成功
     */
    public void success(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 登陆成功
                dialog.dismiss();
                Toast.makeText(MainActivity.this, getString(R.string.welcome)+mUsername.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 登陆失败
     */
    public void failed(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 登陆失败
                dialog.dismiss();
                Toast.makeText(MainActivity.this, R.string.login_fail, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
