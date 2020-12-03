package com.weapon.online_xdclass.service;

import com.weapon.online_xdclass.model.entity.VideoOrder;

import java.util.List;

public interface VideoOrderService {
    int save(int userId,int video_id);

    List<VideoOrder> listOrderByUserId(Integer userId);
}
