package com.goldeneyes.IDao;

import org.apache.ibatis.annotations.Mapper;

import com.goldeneyes.pojo.InfoUpload;

@Mapper
public interface InfoUploadMapper {
    int deleteByPrimaryKey(Integer tabid);

    int insert(InfoUpload record);

    int insertSelective(InfoUpload record);

    InfoUpload selectByPrimaryKey(Integer tabid);

    int updateByPrimaryKeySelective(InfoUpload record);

    int updateByPrimaryKey(InfoUpload record);
}