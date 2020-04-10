/*----------------------------------------------------------------
 *  Copyright (C) 2017山东金视野教育科技股份有限公司
 * 版权所有。 
 *
 * 文件名：
 * 文件功能描述：
 *
 * 
 * 创建标识：
 *
 * 修改标识：
 * 修改描述：
 *----------------------------------------------------------------*/

package com.goldeneyes.service;

import java.util.List;

import com.goldeneyes.vo.AffairApplyVO;
import com.goldeneyes.vo.NoticeDetailVO;
import com.goldeneyes.vo.NoticeManDetailVO;
import com.goldeneyes.vo.NoticeManVO;
import com.goldeneyes.vo.NoticeVO;

/**
 * @author Administrator
 *
 */
public interface NoticeService {
	/**
	 * 新增通知公告
	 * @param schoolId
	 * @param noticeTitle
	 * @param noticeContent
	 * @param noticEncName
	 * @param noticeEncAddr
	 * @param smsSync
	 * @param sendManId
	 * @param sendManName
	 * @param receiveManIds
	 * @param receiveManNames
	 * @return
	 * @throws Exception
	 */
	public int addNotice(String schoolId, String noticeTitle, String noticeContent, String noticEncName, String noticeEncAddr,
			int smsSync, Long sendManId,String sendManCode, String sendManName, String sendManPic, List<Long> receiveManIds,
			List<String> receiveManCodes, List<String> receiveManNames, List<String> receiveManPics)
			throws Exception;

	/**
	 * 撤销通知公告
	 * @param noticeId
	 * @return
	 * @throws Exception
	 */
	public int setNoticeUndo(int noticeId) throws Exception;
	/**
	 * 阅读通知公告
	 * @param noticeId
	 * @return
	 * @throws Exception
	 */
	public int setNoticeRead(int noticeId, Long receiveManId) throws Exception;

	/**
	 * 获取发送的通知公告数量
	 * @param schoolId
	 * @param title
	 * @param status
	 * @param progress
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public int getSendNoticeCnt(String schoolId, String title, Long sendManId, int status, int progress, String beginTime, String endTime)
			throws Exception;

	/**
	 * 获取发送的通知公告列表
	 * @param schoolId
	 * @param title
	 * @param status
	 * @param progress
	 * @param beginTime
	 * @param endTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<NoticeVO> getSendNotice(String schoolId, String title, Long sendManId, int status, int progress, String beginTime,
			String endTime, int pageIndex, int pageSize) throws Exception;

	/**
	 * 获取收到的通知公告数量
	 * @param schoolId
	 * @param receiveManId
	 * @param title
	 * @param sendMan
	 * @param status
	 * @param progress
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public int getReceiveNoticeCnt(String schoolId, Long receiveManId, String title, String sendMan, int status,
			int progress, String beginTime, String endTime) throws Exception;

	/**
	 * 获取收到的通知公告列表
	 * @param schoolId
	 * @param receiveManId
	 * @param title
	 * @param sendMan
	 * @param status
	 * @param progress
	 * @param beginTime
	 * @param endTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<NoticeManVO> getReceiveNotice(String schoolId, Long receiveManId, String title, String sendMan, int status,
			int progress, String beginTime, String endTime, int pageIndex, int pageSize) throws Exception;
	
	/**
	 * 通过ID获取通知公告
	 * @param noticeId
	 * @return
	 * @throws Exception
	 */
	public NoticeDetailVO getNoticeById(int noticeId) throws Exception;
	/**
	 * 通知公告的阅读数
	 * @param noticeId
	 * @return
	 * @throws Exception
	 */
	public int getNoticeReadCntById(int noticeId) throws Exception;
	/**
	 * 获取通知公告被某人已读
	 * @param noticeId
	 * @param receiveManId
	 * @return
	 * @throws Exception
	 */
	public int getNoticeReadCntByMan(int noticeId, Long receiveManId) throws Exception;
	/**
	 * 删除通知公告
	 * @param noticeIds
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public int delNotices(List<Integer> noticeIds, int status) throws Exception;
	/**
	 * 获取全部通告数量
	 * @param schoolId
	 * @param title
	 * @param sendMan
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public int getAllNoticeCnt(String schoolId, String title, String sendMan, String beginTime, String endTime, int status)
			throws Exception;
	/**
	 * 获取全部通告列表
	 * @param schoolId
	 * @param title
	 * @param sendMan
	 * @param beginTime
	 * @param endTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<NoticeVO> getAllNotice(String schoolId, String title, String sendMan, String beginTime, String endTime, int pageIndex, int pageSize, int status)
			throws Exception;
	/**
	 * 通过通知公告接收表ID获取通知公告
	 * @param noticeManId
	 * @return
	 * @throws Exception
	 */
	public NoticeManDetailVO getNoticeByReceiveId(int noticeManTid) throws Exception;
	/**
	 * 获取未读数量
	 * @param schoolId
	 * @param receiveManId
	 * @return
	 * @throws Exception
	 */
	public int getNoReadCntByMan(String schoolId, Long receiveManId) throws Exception;
	/**
	 * 回复通知公告
	 * @param noticeId
	 * @param receiveManId
	 * @param replyContent
	 * @return
	 * @throws Exception
	 */
	public int setNoticeReply(int noticeId, Long receiveManId, String replyContent) throws Exception;
	/**
	 * 真实删除通知公告
	 * @param noticeIds
	 * @return
	 * @throws Exception
	 */
	public int realDelNotices(List<Integer> noticeIds) throws Exception;

}
