package com.goldeneyes.IDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.goldeneyes.pojo.NoticeMan;

@Mapper
public interface NoticeManMapper {
    int deleteByPrimaryKey(Integer tabid);

    int insert(NoticeMan record);

    int insertSelective(NoticeMan record);

    NoticeMan selectByPrimaryKey(Integer tabid);

    int updateByPrimaryKeySelective(NoticeMan record);

    int updateByPrimaryKey(NoticeMan record);
    /**
     * 新增通知接收人
     * @param record
     * @return
     */
    int addNoticeMan(NoticeMan record);
    /**
     * 阅读通知公告
     * @param noticeId
     * @return
     */
    int setNoticeRead(NoticeMan record);
    /**
     * 获取通知阅读列表
     * @param noticeId
     * @param type
     * @return
     */
    List<NoticeMan> getReadList(int noticeId);
    /**
     * 通知公告的阅读数
     * @param noticeId
     * @return
     */
    int getNoticeReadCntById(int noticeId, int status);
    /**
     * 是否被某人已读
     * @param noticeId
     * @param receiveManId
     * @return
     */
    int getNoticeReadCntByMan(int noticeId, Long receiveManId);
    /**
     * 删掉通知下的通知人
     * @return
     */
    int delNoticeManByNotice(int noticeId);
    /**
     * 获取通知未读数量
     * @param noticeId
     * @return
     */
    int getNoticeNoReadCntById(int noticeId);
    /**
     * 通过ID获取通知公告ID
     * @param noticeManId
     * @return
     */
    int getNoticeIdById(int noticeManId);
    /**
     * 通过ID获取阅读状态
     * @param noticeManId
     * @return
     */
    int getReadStatusById(int noticeManId);
    /**
     * 获取未读数量
     * @param schoolId
     * @param receiveManId
     * @return
     */
    int getNoticeNoReadCntByMan(String schoolId, Long receiveManId);
    /**
     * 回复通知公告
     * @param record
     * @return
     */
    int setNoticeReply(NoticeMan record);
    /**
     * 真实删除通知公告的通知人
     * @param params
     * @return
     */
    int realDelNoticeMans(Map<String, Object> params);
}