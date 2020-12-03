package com.weapon.online_xdclass.service.Impl;


import com.weapon.online_xdclass.config.CacheKeyManager;
import com.weapon.online_xdclass.model.entity.Video;
import com.weapon.online_xdclass.model.entity.VideoBanner;
import com.weapon.online_xdclass.mapper.VideoMapper;
import com.weapon.online_xdclass.service.VideoService;
import com.weapon.online_xdclass.utils.BaseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private BaseCache baseCache;

    @Override
    public List<Video> listVideo() {
        try {
            //先根据key去找，如果找不到就去数据库中找，匿名内部类，lambada表达式
            Object cacheObject = baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_VIDEO_LIST, () -> {
                List<Video> listVideo = videoMapper.listVideo();
                System.out.println("从数据库里面找轮list列表");
                return listVideo;
            });

            if(cacheObject instanceof List){
                List<Video> listVideo = (List<Video>)cacheObject;
                return listVideo;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //出异常返回null吧
        return null;
    }

    @Override
    public List<VideoBanner> listBanner() {

        try {
            //先根据key去找，如果找不到就去数据库中找，匿名内部类，lambada表达式
            Object cacheObject = baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_BANNER_KEY, () -> {
                List<VideoBanner> bannerList = videoMapper.listBanner();
                System.out.println("从数据库里面找轮播图列表");
                return bannerList;
            });

            if(cacheObject instanceof List){
                List<VideoBanner> bannerList = (List<VideoBanner>)cacheObject;
                return bannerList;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //出异常返回null吧,可以返回兜底数据，业务系统降级->SpringCloud专题课程
        return null;
    }

    @Override
    public Video findDetailById(int videoId) {

        //格式化一下cacheKey
        //单独构建一个缓存key，每个视频的key是不一样的
        String videoCacheKey = String.format(CacheKeyManager.INDEX_VIDEO_DETAIL,videoId);

        try {
            //先根据key去找，如果找不到就去数据库中找，匿名内部类，lambada表达式
            Object cacheObject = baseCache.getOneHourCache().get(videoCacheKey, () -> {
                Video video = videoMapper.findDetailById(videoId);
                System.out.println("从数据库里面找轮播图列表");
                return video;
            });

            if(cacheObject instanceof Video){
                Video video = (Video)cacheObject;
                return video;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //出异常返回null吧,可以返回兜底数据，业务系统降级->SpringCloud专题课程
        return null;
    }
}
