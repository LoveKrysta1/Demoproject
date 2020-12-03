package com.weapon.online_xdclass.service;

import com.weapon.online_xdclass.model.entity.Video;
import com.weapon.online_xdclass.model.entity.VideoBanner;

import java.util.List;

public interface VideoService  {

    List<Video> listVideo();

    List<VideoBanner> listBanner();

    Video findDetailById(int videoId);
}
