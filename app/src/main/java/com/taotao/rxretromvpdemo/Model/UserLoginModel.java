package com.taotao.rxretromvpdemo.Model;

import com.taotao.rxretromvpdemo.Bean.UserInfo;

/**
 * Created by w.pitt on 2016/8/22.
 */
public class UserLoginModel implements IUserLoginModel {
    @Override
    public void login(final String username, final String password, final onLoginListener loginListener) {
        new Thread() {
            @Override
            public void run() {
                //请求网络业务逻辑处理
                if (username.equals("1234") && password.equals("1234")) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setName(username);
                    userInfo.setPwd(password);
                    loginListener.loginSuccess(userInfo);
                } else {
                    loginListener.loginFailed();
                }
                super.run();
            }
        }.start();
    }
}
