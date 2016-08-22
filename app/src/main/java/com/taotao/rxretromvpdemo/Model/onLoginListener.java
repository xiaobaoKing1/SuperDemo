package com.taotao.rxretromvpdemo.Model;

import com.taotao.rxretromvpdemo.Bean.UserInfo;
/**
 * Created by w.pitt on 2016/8/22.
 */
public interface onLoginListener {
    void loginSuccess(UserInfo user);

    void loginFailed();
}
