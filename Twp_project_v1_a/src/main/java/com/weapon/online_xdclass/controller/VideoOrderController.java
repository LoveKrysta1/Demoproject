package com.weapon.online_xdclass.controller;

import com.weapon.online_xdclass.model.entity.VideoOrder;
import com.weapon.online_xdclass.model.request.VideoOrderRequest;
import com.weapon.online_xdclass.service.VideoOrderService;
import com.weapon.online_xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pri/order")
public class VideoOrderController {

    @Autowired
    private VideoOrderService videoOrderService;


    /**
     * 下单接口
     * @return
     */
    @PostMapping("save")
    public JsonData saveOrder(@RequestBody VideoOrderRequest videoOrderRequest, HttpServletRequest request){

        Integer userId = (Integer)request.getAttribute("user_id");

        int rows = videoOrderService.save(userId, videoOrderRequest.getVideoId());

        return rows == 0? JsonData.buildError("下单失败"):JsonData.buildSuccess("下单成功");

    }



    @GetMapping("list")
    public JsonData listOrder(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("user_id");

        List<VideoOrder> videoOrderList = videoOrderService.listOrderByUserId(userId);

        return JsonData.buildSuccess(videoOrderList);
    }

}
