package com.weapon.xdvideo.service.impl;

import com.weapon.xdvideo.config.WeChatConfig;
import com.weapon.xdvideo.domain.User;
import com.weapon.xdvideo.mapper.UserMapper;
import com.weapon.xdvideo.service.UserService;
import com.weapon.xdvideo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User saveWeChatUser(String code) {

        String accessTokenUrl = String.format(weChatConfig.getOpenAccessTokenUrl(),weChatConfig.getOpenAppid(),weChatConfig.getOpenAppsecret(),code);

        //获取access_token
        Map<String, Object> baseMap = HttpUtils.doGet(accessTokenUrl);
        if(baseMap == null || baseMap.isEmpty()){ return null; }
        String accessToken = (String)baseMap.get("access_token");
        String openId = (String)baseMap.get("openid");

        //判断有没有登录过，返回或者更新
        User dbUser = userMapper.findByOpenId(openId);
        if(dbUser != null){//更新用户，直接返回
            return dbUser;
        }



        //获取用户基本信息
        String userInfoUrl = String.format(weChatConfig.getOpenUserInfoUrl(),accessToken,openId);
        Map<String, Object> baseUserMap = HttpUtils.doGet(userInfoUrl);
        if(baseUserMap == null || baseUserMap.isEmpty()){ return null; }
        String nickname = (String)baseUserMap.get("nickname");
        Double sexTemp = (Double)baseUserMap.get("sex");
        //Double 转成int类型
        int sex =sexTemp.intValue();
        String province = (String)baseUserMap.get("province");
        String city = (String)baseUserMap.get("city");
        String country = (String)baseUserMap.get("country");
        String headimgurl = (String)baseUserMap.get("headimgurl");
        StringBuilder sb = new StringBuilder(country).append("||").append(province).append("||").append(city);
        String finalAddress = sb.toString();
        //解决昵称 省份乱码
        try {
            nickname = new String(nickname.getBytes("ISO-8859-1"),"UTF-8");
            finalAddress = new String(finalAddress.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //封装user对象
        User user = new User();
        user.setName(nickname);
        user.setHeadImg(headimgurl);
        user.setSex(sex);
        user.setCity(finalAddress);
        user.setCreateTime(new Date());
        user.setOpenid(openId);

        userMapper.save(user);
        return user;
    }
}
