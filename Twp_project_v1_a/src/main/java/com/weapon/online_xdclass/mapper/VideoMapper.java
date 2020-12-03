package com.weapon.online_xdclass.mapper;

import com.weapon.online_xdclass.model.entity.Video;
import com.weapon.online_xdclass.model.entity.VideoBanner;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoMapper {

    /**
     * 查询视频列表
     * @return
     */
    List<Video> listVideo();

    /**
     * 轮播图
     * @return
     */
    List<VideoBanner> listBanner();

    /**
     * 查询视频详情（三表关联查询）
     * @param videoId
     * @return
     */
    Video findDetailById(@Param("video_id")int videoId);

    /**
     * 简单查询
     * @param videoId
     * @return
     */
    Video findById(@Param("video_id")int videoId);
}
