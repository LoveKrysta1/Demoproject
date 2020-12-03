package com.weapon.online_xdclass.mapper;

import com.weapon.online_xdclass.model.entity.Episode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeMapper {

    Episode findFirstEpisodeById(@Param("video_id") int videoId);


}
