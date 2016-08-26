package com.taotao.rxretromvpdemo.model;

import com.taotao.rxretromvpdemo.bean.UserInfo;
import com.taotao.rxretromvpdemo.presenter.OnNetRequestListener;
import com.taotao.rxretromvpdemo.server.RetrofitService;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by w.pitt on 2016/8/22.
 */
public class UserLoginModel implements IUserLoginModel {
    @Override
    public void login(final String username, final String password, final OnNetRequestListener listener) {

        //使用Rxandroid 响应Retrofit
        Observable<UserInfo> observable = RetrofitService.getInstance().createShowApi().loginApi(RetrofitService.getCacheControl(), username, password, "m");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //同样是在 subscribe() 调用后而且在事件发送前执行，但区别在于它可以指定线程
                //默认情况下， doOnSubscribe() 执行在 subscribe() 发生的线程；而如果在 doOnSubscribe()
                // 之后有 subscribeOn() 的话，它将执行在离它最近的 subscribeOn() 所指定的线程。
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        listener.onStart();
                    }
                }).subscribe(new Subscriber<UserInfo>() {
            @Override
            public void onCompleted() {
                listener.onFinish();
            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e);
                listener.onFinish();
            }

            @Override
            public void onNext(UserInfo userInfo) {
                if (userInfo.getUser() == null) {
                    listener.onFailure(new Exception("可以的"));
                } else {
                    listener.onSuccess(userInfo);
                }
            }
        });

//        new Thread() {
//            @Override
//            public void run() {
//                //请求网络业务逻辑处理
//                if (username.equals("1234") && password.equals("1234")) {
//                    UserInfo userInfo = new UserInfo();
//                    userInfo.setName(username);
//                    userInfo.setPwd(password);
//                    loginListener.loginSuccess(userInfo);
//                } else {
//                    loginListener.loginFailed();
//                }
//                super.run();
//            }
//        }.start();
    }
}

