package com.goldeneyes.IDao;

import org.apache.ibatis.annotations.Mapper;

import com.goldeneyes.pojo.SchoolCalendar;

@Mapper
public interface SchoolCalendarMapper {
    int deleteByPrimaryKey(Integer tabid);

    int insert(SchoolCalendar record);

    int insertSelective(SchoolCalendar record);

    SchoolCalendar selectByPrimaryKey(Integer tabid);

    int updateByPrimaryKeySelective(SchoolCalendar record);

    int updateByPrimaryKey(SchoolCalendar record);
}