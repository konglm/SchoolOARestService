package com.goldeneyes.IDao;

import org.apache.ibatis.annotations.Mapper;

import com.goldeneyes.pojo.InfoCollect;

@Mapper
public interface InfoCollectMapper {
    int deleteByPrimaryKey(Integer tabid);

    int insert(InfoCollect record);

    int insertSelective(InfoCollect record);

    InfoCollect selectByPrimaryKey(Integer tabid);

    int updateByPrimaryKeySelective(InfoCollect record);

    int updateByPrimaryKey(InfoCollect record);
}