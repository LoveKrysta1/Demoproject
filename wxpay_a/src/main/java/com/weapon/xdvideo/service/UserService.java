package com.weapon.xdvideo.service;


import com.weapon.xdvideo.domain.User;

/**
 * 用户业务接口类
 */
public interface UserService {
    User saveWeChatUser(String code);
}
