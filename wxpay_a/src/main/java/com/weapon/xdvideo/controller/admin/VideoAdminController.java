package com.weapon.xdvideo.controller.admin;

import com.weapon.xdvideo.domain.Video;
import com.weapon.xdvideo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/v1/video")
public class VideoAdminController {

    @Autowired
    private VideoService videoService;

    /**
     * 根据id删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("del_by_id")
    public Object delById(@RequestParam(value = "video_id" ,required=true)int videoId){
        return videoService.delete(videoId);
    }

    /**
     * 根据id更新视频
     * RequestBody 请求体映射实体类
     * 	需要指定http头为 content-type 为application/json charset=utf-8
     * @param video
     * @return
     */
    @PutMapping("update_by_id")
    public Object update(@RequestBody Video video) {

        return videoService.update(video);
    }

    /**
     * 添加视频
     * @param video
     * @return
     */
    @PostMapping("save")
    public Object save(@RequestBody Video video){

        return videoService.save(video);
    }
}