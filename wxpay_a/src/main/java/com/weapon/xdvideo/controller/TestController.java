package com.weapon.xdvideo.controller;


import com.weapon.xdvideo.config.WeChatConfig;
import com.weapon.xdvideo.mapper.VideoMapper;
import com.weapon.xdvideo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private VideoMapper videoMapper;

    @RequestMapping("test_config")
    public JsonData testConfig(){
        System.out.println(weChatConfig.getAppId()+" "+weChatConfig.getAppsecret());
        return JsonData.buildSuccess(weChatConfig.getAppId());
    }

    @RequestMapping("test_db")
    public Object testDb(){
        return videoMapper.findAll();
    }
}
