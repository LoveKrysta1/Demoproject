package com.weapon.online_xdclass.service;

import com.weapon.online_xdclass.model.entity.User;

import java.util.Map;

public interface UserService {

    /**
     * 注册用户
     */
    int save(Map<String,String> userInfo);

    String findByPhoneAndPwd(String phone, String pwd);

    User findUserInfoByToken(Integer userId);
}
