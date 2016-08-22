package com.taotao.rxretromvpdemo.Presenter;

import android.os.Handler;

import com.taotao.rxretromvpdemo.Bean.UserInfo;
import com.taotao.rxretromvpdemo.Model.IUserLoginModel;
import com.taotao.rxretromvpdemo.Model.UserLoginModel;
import com.taotao.rxretromvpdemo.Model.onLoginListener;
import com.taotao.rxretromvpdemo.VIew.IUserView;

/**
 * Created by w.pitt on 2016/8/22.
 */
public class UserLoginPresenter {
    public IUserView mUserView;
    public IUserLoginModel mUserLoginModel;
    private Handler mHandler = new Handler();

    public UserLoginPresenter(IUserView userView) {
        this.mUserView = userView;
        this.mUserLoginModel = new UserLoginModel();
    }

    public void login() {
        mUserView.showLoading();
        mUserLoginModel.login(mUserView.getUserName(), mUserView.getPassword(), new onLoginListener() {
            @Override
            public void loginSuccess(final UserInfo user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mUserView.hideLoading();
                        mUserView.toMainAct(user);
                    }
                });
            }

            @Override
            public void loginFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mUserView.hideLoading();
                        mUserView.errorLogin();
                    }
                });
            }
        });
    }
}
