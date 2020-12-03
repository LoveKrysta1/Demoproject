package com.weapon.xdvideo.provider;

import com.weapon.xdvideo.domain.Video;
import org.apache.ibatis.jdbc.SQL;

/**
 * Video构建动态sql语句
 */
public class VideoProvider {


    /**
     * 更新video动态语句
     * @param video
     * @return
     */
    public String updateVideo(final Video video){
        return new SQL(){{
            UPDATE("video");

            //条件写法.
            if(video.getTitle()!= null){
                SET("title=#{title}");
            }
            if(video.getSummary()!= null){
                SET("summary=#{summary}");
            }
            if(video.getCoverImg()!= null){
                SET("cover_img=#{coverImg}");
            }
            if(video.getViewNum()!= null){
                SET("view_num=#{ViewNum}");
            }
            if(video.getPoint()!= null){
                SET("point=#{point}");
            }
            if(video.getPrice()!= null){
                SET("price=#{price}");
            }
            if(video.getOnline()!= null){
                SET("online=#{online}");
            }


            WHERE("id=#{id}");
        }}.toString();
    }

}
