package com.taotao.rxretromvpdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.taotao.rxretromvpdemo.bean.UserInfo;
import com.taotao.rxretromvpdemo.presenter.UserLoginPresenter;
import com.taotao.rxretromvpdemo.view.IUserView;
import com.taotao.rxretromvpdemo.view.MainAct;

public class LoginAct extends AppCompatActivity implements IUserView, View.OnClickListener {
    private ProgressDialog progressDialog;
    private EditText et_accounts;
    private EditText et_password;
    private Button btn_login;
    private UserLoginPresenter mUserLoginProsenter = new UserLoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initLoading();
    }

    private void initLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this, R.style.theme_customer_progress_dialog);
            //progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("登陆中...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
    }

    private void initView() {
        //初始化组件
        et_accounts = (EditText) findViewById(R.id.et_accounts);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        initListener();
    }

    private void initListener() {
        btn_login.setOnClickListener(this);
    }

    @Override
    public String getUserName() {
        return et_accounts.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.hide();
    }

    @Override
    public void toMainAct(UserInfo user) {
        startActivity(new Intent(LoginAct.this, MainAct.class));
        Toast.makeText(LoginAct.this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorLogin() {
        Toast.makeText(LoginAct.this, "登录失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String s = et_accounts.getText().toString();
                if (s.equals("")) {
                    Toast.makeText(LoginAct.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }

                String password = et_password.getText().toString();
                if (password.equals("")) {
                    Toast.makeText(LoginAct.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                mUserLoginProsenter.login();
                break;
        }
    }
}
