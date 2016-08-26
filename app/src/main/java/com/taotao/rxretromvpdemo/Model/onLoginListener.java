package com.taotao.rxretromvpdemo.model;

import com.taotao.rxretromvpdemo.bean.UserInfo;
/**
 * Created by w.pitt on 2016/8/22.
 */
public interface onLoginListener {
    void loginSuccess(UserInfo user);

    void loginFailed();
}
