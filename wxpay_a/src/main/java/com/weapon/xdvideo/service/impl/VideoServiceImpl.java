package com.weapon.xdvideo.service.impl;

import com.weapon.xdvideo.domain.Video;
import com.weapon.xdvideo.mapper.VideoMapper;
import com.weapon.xdvideo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> findAll() {
        return videoMapper.findAll();
    }

    @Override
    public Video findById(int id) {
        return videoMapper.findById(id);
    }

    @Override
    public int update(Video video) {
        return videoMapper.update(video);
    }

    @Override
    public int delete(int id) {
        return videoMapper.delete(id);
    }

    @Override
    public int save(Video video) {
        //正常来说拿不到自增id，需要格外配置
        System.out.println("保存对象的id=" + video.getId());
        return videoMapper.save(video);
    }

}
