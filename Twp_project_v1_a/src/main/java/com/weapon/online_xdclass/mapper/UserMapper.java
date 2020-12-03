package com.weapon.online_xdclass.mapper;

import com.weapon.online_xdclass.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    /**
     * 注册用户
     */
    int save(User user);

    User findByPhone(@Param("phone") String phone);

    //养成良好习惯 ，加Param
    User findByPhoneAndPwd(@Param("phone") String phone, @Param("pwd") String pwd);

    User findUserInfoByToken(@Param("user_id") Integer userId);
}
