package com.weapon.online_xdclass.controller;

import com.weapon.online_xdclass.model.entity.Video;
import com.weapon.online_xdclass.model.entity.VideoBanner;
import com.weapon.online_xdclass.service.BloomFilterService;
import com.weapon.online_xdclass.service.VideoService;
import com.weapon.online_xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// loaclhost:8081
@RestController
@RequestMapping("api/v1/pub/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private BloomFilterService bloomFilterService;

    /**
     * 轮播图列表
     * @return
     */
    @GetMapping("list_banner")
    public JsonData listBanner(){
        List<VideoBanner> bannerList = videoService.listBanner();
//        int i = 1/0;


        return JsonData.buildSuccess(bannerList);
    }

    /**
     * 视频聊表
     * @return
     */
    @GetMapping("list")
    public Object videoList(){

        List<Video> videoList = videoService.listVideo();
        return JsonData.buildSuccess(videoList);
    }


    /**
     * 查询视频详情
     * @param videoId
     * @return
     */
    @GetMapping("find_detail_by_id")
    public JsonData findDetailById(@RequestParam(value = "video_id",required = true)int videoId){
        if(bloomFilterService.videoIdExists(videoId)){
            //需要使用mybatis关联复杂查询
            Video video = videoService.findDetailById(videoId);

            return JsonData.buildSuccess(video);
        }
       return JsonData.buildError(-1,"fuck");

    }
}
