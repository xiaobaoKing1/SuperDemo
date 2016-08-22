package com.taotao.rxretromvpdemo.Model;

/**
 * Created by w.pitt on 2016/8/22.
 */
public interface IUserLoginModel {
    public void login(String username, String passwordm, onLoginListener loginListener);
}
