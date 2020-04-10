package com.goldeneyes.IDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.goldeneyes.pojo.Notice;
import com.goldeneyes.vo.NoticeAndNoticeManVO;
import com.goldeneyes.vo.NoticeVO;

@Mapper
public interface NoticeMapper {
    int deleteByPrimaryKey(Integer tabid);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Integer tabid);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);
    /**
     * 新增通知公告
     * @param record
     * @return
     */
    int addNotice(Notice record);
    /**
     * 屏蔽通知公告
     * @param noticeId
     * @return
     */
    int setNoticeUndo(int noticeId);
    /**
     * 获取发送的通知公告数量
     * @param params
     * @return
     */
    int getSendNoticeCnt(Map<String, Object> params);
    /**
     * 获取发送的通知公告列表
     * @param params
     * @return
     */
    List<Notice> getSendNotice(Map<String, Object> params);
    /**
     * 获取收到的通知公告数量
     * @param params
     * @return
     */
    int getReceiveNoticeCnt(Map<String, Object> params);
    /**
     * 获取收到的通知公告列表
     * @param params
     * @return
     */
    List<NoticeAndNoticeManVO> getReceiveNotice(Map<String, Object> params);
    /**
     * 删除通知公告
     * @param noticeId
     * @return
     */
    int delNotice(Map<String, Object> params);
    /**
     * 获取全部通告数量
     * @param params
     * @return
     */
    int getAllNoticeCnt(Map<String, Object> params);
    /**
     * 获取全部通告列表
     * @param params
     * @return
     */
    List<NoticeVO> getAllNotice(Map<String, Object> params);
    /**
     * 设置阅读状态
     * @param noticeId
     * @param status
     * @return
     */
    int setNoticeStatus(int noticeId, int status);
    /**
     * 真实删除通知公告
     * @param params
     * @return
     */
    int realDelNotices(Map<String, Object> params);
}