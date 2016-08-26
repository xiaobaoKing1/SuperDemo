package com.taotao.rxretromvpdemo.server;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.taotao.rxretromvpdemo.MyApplication;
import com.taotao.rxretromvpdemo.common.BizInterface;
import com.taotao.rxretromvpdemo.utils.NetUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * 网络请求引擎类
 * Created by w.pitt on 2016/8/23.
 */
public class RetrofitService {
    //设置缓存有效期两天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    protected static final String   CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private static OkHttpClient mOkHttpClient;

    private RetrofitService() {
    }

    private volatile static RetrofitService instance = null;

    public static RetrofitService getInstance() {
        if (instance == null) {
            synchronized (RetrofitService.class) {
                if (instance == null) {
                    instance = new RetrofitService();
                }
            }
        }
        return instance;
    }


    private volatile static ShowApi showApi = null;

    public static ShowApi createShowApi() {
        if (showApi == null) {
            synchronized (RetrofitService.class) {
                if (showApi == null) {
                    // 初始化 okhttpclient
                    initOkHttpClient();
                    showApi = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            .baseUrl(BizInterface.base)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build().create(ShowApi.class);
                }
            }
        }
        return showApi;
    }

    private static void initOkHttpClient() {
        if (mOkHttpClient == null) {
            //okhttpclient静态创建一次即可
            //设置缓存路径
            File cacheFile = new File(MyApplication.getContext().getCacheDir(), "HttpCache");
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);

            //云端响应拦截器， 用来配置缓存策略
            Interceptor rewriteCacheControlInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    //判断网络是否连接
                    if (!NetUtil.isConnected(MyApplication.getContext())) {
                        //无网络
                        request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                    }
                    /**
                     * Interceptor 接口只包含一个方法 intercept，其参数是 Chain 对象。
                     * Chain 对象表示的是当前的拦截器链条。通过 Chain 的 request 方法
                     * 可以获取到当前的 Request 对象。在使用完 Request 对象之后，通过
                     * Chain 对象的 proceed 方法来继续拦截器链条的执行。当执行完成之后
                     * ，可以对得到的 Response 对象进行额外的处理
                     */
                    Response originalResponse = chain.proceed(request);
                    if (NetUtil.isConnected(MyApplication.getContext())) {
                        //有网的时候读入接口上的Headers里的配置。
                        String cacheControl = request.cacheControl().toString();
                        return originalResponse.newBuilder().header("Cache-Control", cacheControl).removeHeader("Pragma").build();
                    } else {
                        //没有网的话  只从缓存中读取
                        return originalResponse.newBuilder().header("Cache-Control", "public, only-if-cached," + CACHE_STALE_SEC)
                                .removeHeader("Pragma").build();
                    }
                }
            };
            mOkHttpClient = new OkHttpClient();
            //设置缓存大小
            mOkHttpClient.setCache(cache);
            //设置网络拦截器
            mOkHttpClient.networkInterceptors().add(rewriteCacheControlInterceptor);
            //应用拦截器
            mOkHttpClient.interceptors().add(rewriteCacheControlInterceptor);
            //设置超时时间
            mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        }
    }
    /**
     * 根据网络状况获取缓存的策略
     */
        public static String getCacheControl(){
        return NetUtil.isConnected(MyApplication.getContext())?CACHE_CONTROL_NETWORK:CACHE_CONTROL_CACHE;
    }
}
