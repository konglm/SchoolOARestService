package com.goldeneyes.IDao;

import org.apache.ibatis.annotations.Mapper;

import com.goldeneyes.pojo.Announcement;

@Mapper
public interface AnnouncementMapper {
    int deleteByPrimaryKey(Integer tabid);

    int insert(Announcement record);

    int insertSelective(Announcement record);

    Announcement selectByPrimaryKey(Integer tabid);

    int updateByPrimaryKeySelective(Announcement record);

    int updateByPrimaryKey(Announcement record);
}