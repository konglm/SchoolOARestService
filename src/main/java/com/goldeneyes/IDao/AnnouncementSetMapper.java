package com.goldeneyes.IDao;

import org.apache.ibatis.annotations.Mapper;

import com.goldeneyes.pojo.AnnouncementSet;

@Mapper
public interface AnnouncementSetMapper {
    int deleteByPrimaryKey(Integer tabid);

    int insert(AnnouncementSet record);

    int insertSelective(AnnouncementSet record);

    AnnouncementSet selectByPrimaryKey(Integer tabid);

    int updateByPrimaryKeySelective(AnnouncementSet record);

    int updateByPrimaryKey(AnnouncementSet record);
}