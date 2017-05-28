package com.itheima.mvp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itheima.mvp.dagger2.component.DaggerMainActivityComponent;
import com.itheima.mvp.dagger2.module.MainActivityModule;
import com.itheima.mvp.presenter.MainActivityPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.username)
    EditText mUsername;
    @InjectView(R.id.password)
    EditText mPassword;


    private ProgressDialog dialog;

    // 第一步：指定需要注入的目标
    @Inject
    MainActivityPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


        dialog = new ProgressDialog(this);

        // 此处会实例化具体P层的对象，一旦new出来了，两层之间也就耦合到一起了。
//         presenter = new MainActivityPresenter(this);

        DaggerMainActivityComponent component = (DaggerMainActivityComponent) DaggerMainActivityComponent.builder().mainActivityModule(new MainActivityModule(this)).build();
        component.in(this);
    }

    /**
     * 钮点击
     *
     * @param view
     */
    public void login(View view) {
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        boolean checkUserInfo = checkUserInfo(username, password);

        if (checkUserInfo) {
            dialog.show();
            presenter.login(username, password);
        } else {
            Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 检验用户输入——界面相关逻辑处理
     *
     * @param username
     * @param password
     * @return
     */
    private boolean checkUserInfo(String username, String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }

    /**
     * 登陆成功
     */
    public void success() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 登陆成功
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "欢迎回来:" + mUsername.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 登陆失败
     */
    public void failed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 登陆失败
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "用户名或密码输入有误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
