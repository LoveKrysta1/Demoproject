package com.weapon.xdvideo.service;
/**
 * 订单接口
 */

import com.weapon.xdvideo.domain.VideoOrder;
import com.weapon.xdvideo.dto.VideoOrderDto;

import java.util.List;

public interface VideoOrderService {

    /**
     * 下单接口
     * @param videoOrderDto
     * @return
     */
    String save(VideoOrderDto videoOrderDto) throws Exception;

    /**
     * 根据流水好查找订单
     * @param outTradeNo
     * @return
     */
    VideoOrder findByOutTradeNo(String outTradeNo);

    /**
     * 根据流水号更新订单
     * @param videoOrder
     * @return
     */
    int updateVideoOrderByOutTradeNo(VideoOrder videoOrder);



}
