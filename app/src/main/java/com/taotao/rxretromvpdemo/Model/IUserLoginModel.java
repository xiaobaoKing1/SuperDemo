package com.taotao.rxretromvpdemo.model;

import com.taotao.rxretromvpdemo.presenter.OnNetRequestListener;

/**
 * Created by w.pitt on 2016/8/22.
 */
public interface IUserLoginModel {
    public void login(String username, String passwordm, OnNetRequestListener listenre);
}
