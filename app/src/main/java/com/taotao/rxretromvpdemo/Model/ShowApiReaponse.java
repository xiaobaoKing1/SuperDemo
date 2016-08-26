package com.taotao.rxretromvpdemo.model;

/**
 * Created by w.pitt on 2016/8/24.
 * 套接   接口统一的错误信息  或者 请求状态
 */
public class ShowApiReaponse<T> {
    public String success;
    public String error;
    public T showapi_res_body;
}
