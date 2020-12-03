package com.weapon.online_xdclass.controller;

import com.weapon.online_xdclass.model.entity.User;
import com.weapon.online_xdclass.model.request.LoginRequest;
import com.weapon.online_xdclass.service.UserService;
import com.weapon.online_xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api/v1/pri/user")
public class UserController {

    @Autowired
    private UserService userService;

    //用于post方式提交上来的  要@RequestBody
    @PostMapping("register")
    public JsonData register(@RequestBody Map<String,String> userInfo){

        int rows = userService.save(userInfo);
        return rows == 1 ? JsonData.buildSuccess() : JsonData.buildError("失败");
    }


    /**
     *登录接口
     */
    @PostMapping("login")
    public JsonData login(@RequestBody LoginRequest loginRequest){

        String token = userService.findByPhoneAndPwd(loginRequest.getPhone(),loginRequest.getPwd());

        return token == null ? JsonData.buildError("登录失败，账号密码错误"):JsonData.buildSuccess(token);
    }


    /**
     * 根据用户id查询用户信息
     * @param request
     * @return
     */
    @GetMapping("find_by_token")
    public JsonData findUserInfoByToken(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("user_id");

        if(userId == null){
            return JsonData.buildError("查询失败");
        }
        User user = userService.findUserInfoByToken(userId);
        return JsonData.buildSuccess(user);

    }
}
