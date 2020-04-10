package com.goldeneyes.IDao;

import org.apache.ibatis.annotations.Mapper;

import com.goldeneyes.pojo.CooperateNoticeReply;

@Mapper
public interface CooperateNoticeReplyMapper {
    int deleteByPrimaryKey(Integer tabid);

    int insert(CooperateNoticeReply record);

    int insertSelective(CooperateNoticeReply record);

    CooperateNoticeReply selectByPrimaryKey(Integer tabid);

    int updateByPrimaryKeySelective(CooperateNoticeReply record);

    int updateByPrimaryKey(CooperateNoticeReply record);
}