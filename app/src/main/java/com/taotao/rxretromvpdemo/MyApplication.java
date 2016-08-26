package com.taotao.rxretromvpdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by w.pitt on 2016/8/23.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    public static String cacheDir = "";

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        if (getApplicationContext().getExternalCacheDir() != null && isExistSDCard()) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }
    }

    private boolean isExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    // 获取ApplicationContext
    public static Context getContext() {
        return instance;
    }
}
