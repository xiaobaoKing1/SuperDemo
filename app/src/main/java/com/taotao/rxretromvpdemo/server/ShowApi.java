package com.taotao.rxretromvpdemo.server;

import com.taotao.rxretromvpdemo.bean.UserInfo;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by w.pitt on 2016/8/23.
 */
public interface ShowApi {
    /**
     * 登录
     */
    @GET(URLutils.login)
    Observable<UserInfo> loginApi(@Header("Cache-Control") String cacheControl,
                                                  @Query("User") String User,
                                                  @Query("Pwd") String Pwd,
                                                  @Query("frm") String frm);

}
