package com.weapon.online_xdclass.config;

/**
 * 缓存key管理类
 */

public class CacheKeyManager {
    //模块加功能进行划分，index  功能  返回类型
    /**
     *首页轮播图缓存key
     */
    public static final String INDEX_BANNER_KEY = "index:banner:list";

    /**
     * 首页视频列表缓存key
     */
    public static final String INDEX_VIDEO_LIST = "index:video:list";

    /**
     * 视频详情缓存key，%s是视频id
     */
    public static final String INDEX_VIDEO_DETAIL = "index:videoDetail:%s";

}
