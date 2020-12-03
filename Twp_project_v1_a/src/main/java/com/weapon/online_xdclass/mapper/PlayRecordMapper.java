package com.weapon.online_xdclass.mapper;


import com.weapon.online_xdclass.model.entity.PlayRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayRecordMapper {

    int saveRecord(PlayRecord playRecord);


}
