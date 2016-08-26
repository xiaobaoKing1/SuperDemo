package com.taotao.rxretromvpdemo.common;

/**
 * Created by w.pitt on 2016/8/23.
 */
public interface BizInterface {
    String base = "http://crm3.cjatech.com/";
    /**
     * 百度API接口
     */
    String API = "http://apis.baidu.com";
    /**
     * 开发者API密钥
     */
    String API_KEY = "4720bdbcfb3aa457eefd38d2f8fa580f";
    /**
     * 新闻接口
     * 服务商： 易源接口
     */
    String NEWS_URL = "/showapi_open_bus/channel_news/search_news";
    /**
     * 天气预报 (根据地名)
     * 服务商： 易源接口
     */
    String WEATHER_URL = "/showapi_open_bus/weather_showapi/address";

    String PICTURES_URL = "/showapi_open_bus/pic/pic_search";


}

