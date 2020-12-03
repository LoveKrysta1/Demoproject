package com.weapon.online_xdclass.service.Impl;

import com.weapon.online_xdclass.exception.XDException;
import com.weapon.online_xdclass.mapper.EpisodeMapper;
import com.weapon.online_xdclass.mapper.PlayRecordMapper;
import com.weapon.online_xdclass.mapper.VideoMapper;
import com.weapon.online_xdclass.mapper.VideoOrderMapper;
import com.weapon.online_xdclass.model.entity.Episode;
import com.weapon.online_xdclass.model.entity.PlayRecord;
import com.weapon.online_xdclass.model.entity.Video;
import com.weapon.online_xdclass.model.entity.VideoOrder;
import com.weapon.online_xdclass.service.VideoOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class VideoOrderServiceImpl implements VideoOrderService {


    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private EpisodeMapper episodeMapper;

    @Autowired
    private PlayRecordMapper playRecordMapper;


    /**
     * 下单操作
     * 未来版本：优惠券抵扣，风控用户检查，生成订单基础信息，生成支付信息
     */
    @Override
    @Transactional//放在方法
    public int save(int userId, int videoId) {
        //判断是否已经购买
        VideoOrder videoOrder = videoOrderMapper.findByUserIdAndVideoIdAndState(userId,videoId,1);
        if(videoOrder != null ) return 0;

        Video video = videoMapper.findById(videoId);
        VideoOrder newVideoOrder = new VideoOrder();
        newVideoOrder.setOutTradeNo(UUID.randomUUID().toString());
        newVideoOrder.setState(1);
        newVideoOrder.setTotalFee(video.getPrice());
        newVideoOrder.setUserId(userId);
        newVideoOrder.setVideoId(videoId);
        newVideoOrder.setCreateTime(new Date());
        newVideoOrder.setVideoTitle(video.getTitle());
        newVideoOrder.setVideoCoverImg(video.getCoverImg());

        int rows = videoOrderMapper.saveOrder(newVideoOrder);

        //成功购买插入后 加入记录端口，播放记录，又要进行插入,生成播放记录
        if(rows == 1){
            Episode episode = episodeMapper.findFirstEpisodeById(videoId);
            if(episode == null){
                throw new XDException(-1,"视频没有集信息，轻运营人员检查");
            }

            PlayRecord playRecord = new PlayRecord();
            playRecord.setCreateTime(new Date());
            playRecord.setEpisodeId(episode.getId());
            playRecord.setCurrentNum(episode.getNum());
            playRecord.setUserId(userId);
            playRecord.setVideoId(videoId);
            playRecordMapper.saveRecord(playRecord);
        }

        return rows;
    }

    @Override
    public List<VideoOrder> listOrderByUserId(Integer userId) {
        return videoOrderMapper.listOrderByUserId(userId);
    }
}
