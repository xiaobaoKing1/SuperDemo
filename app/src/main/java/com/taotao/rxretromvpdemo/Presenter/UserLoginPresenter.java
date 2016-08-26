package com.taotao.rxretromvpdemo.presenter;

import android.os.Handler;
import android.widget.Toast;

import com.taotao.rxretromvpdemo.MyApplication;
import com.taotao.rxretromvpdemo.bean.UserInfo;
import com.taotao.rxretromvpdemo.model.IUserLoginModel;
import com.taotao.rxretromvpdemo.model.UserLoginModel;
import com.taotao.rxretromvpdemo.view.IUserView;
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
        mUserLoginModel.login(mUserView.getUserName(), mUserView.getPassword(), new OnNetRequestListener<UserInfo>() {
            @Override
            public void onStart() {
                mUserView.showLoading();
            }

            @Override
            public void onFinish() {
                mUserView.hideLoading();
            }

            @Override
            public void onSuccess(UserInfo data) {
                mUserView.toMainAct(data);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MyApplication.getContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
