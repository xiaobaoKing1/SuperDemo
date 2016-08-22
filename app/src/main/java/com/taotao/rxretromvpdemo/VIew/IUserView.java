package com.taotao.rxretromvpdemo.VIew;

import com.taotao.rxretromvpdemo.Bean.UserInfo;

/**
 * Created by w.pitt on 2016/8/22.
 */
public interface IUserView {
    //定义了一套接口方法，通过activiry和fragment中实现接口进行具体的操作
    // 获取账号
    String getUserName();

    //获取密码
    String getPassword();

    //设置loading
    void showLoading();

    void hideLoading();

    void toMainAct(UserInfo user);

    void errorLogin();
}
