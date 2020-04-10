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

package com.goldeneyes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goldeneyes.IDao.NoticeManMapper;
import com.goldeneyes.IDao.NoticeMapper;
import com.goldeneyes.pojo.Notice;
import com.goldeneyes.pojo.NoticeMan;
import com.goldeneyes.service.NoticeService;
import com.goldeneyes.util.CommonTool;
import com.goldeneyes.vo.NoticeAndNoticeManVO;
import com.goldeneyes.vo.NoticeDetailVO;
import com.goldeneyes.vo.NoticeManDetailVO;
import com.goldeneyes.vo.NoticeManVO;
import com.goldeneyes.vo.NoticeVO;

/**
 * @author Administrator
 *
 */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	@Resource
	NoticeMapper noticeMapper;
	@Resource
	NoticeManMapper noticeManMapper;

	/**
	 *  @author Administrator
	 */
	@Override
	public int addNotice(String schoolId, String noticeTitle, String noticeContent, String noticEncName, String noticeEncAddr,
			int smsSync, Long sendManId,String sendManCode, String sendManName, String sendManPic, List<Long> receiveManIds,
			List<String> receiveManCodes, List<String> receiveManNames, List<String> receiveManPics) throws Exception {
		// TODO Auto-generated method stub
		//先新增通知公告
		Notice notice = new Notice();
		notice.setNoticecontent(noticeContent);
		notice.setNoticeencaddr(noticeEncAddr);
		notice.setNoticeencname(noticEncName);
		notice.setNoticetitle(noticeTitle);
		notice.setSchoolid(schoolId);
		notice.setSendmanid(sendManId);
		notice.setSendmancode(sendManCode);
		notice.setSendmanname(sendManName);
		notice.setSendmanpic(sendManPic);
		notice.setSendtime(new Date());
		notice.setSmssync(CommonTool.int2byte(smsSync));
		notice.setProgress(CommonTool.int2byte(1));
		notice.setStatus(CommonTool.int2byte(1));
		noticeMapper.addNotice(notice);
		int maxId = notice.getTabid();
		
		//再新增通知接收人
		for(int i = 0; i < receiveManIds.size(); i++){
			NoticeMan noticeMan = new NoticeMan();
			noticeMan.setNoticeid(maxId);
			noticeMan.setReceivemanid(receiveManIds.get(i));
			noticeMan.setReceivemancode(receiveManCodes.get(i));
			noticeMan.setReceivemanname(receiveManNames.get(i));
			noticeMan.setReceivemanpic(receiveManPics.get(i));
			noticeMan.setStatus(CommonTool.int2byte(0));
			noticeManMapper.addNoticeMan(noticeMan);
		}
		
		return maxId;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int setNoticeUndo(int noticeId) throws Exception {
		// TODO Auto-generated method stub
		noticeMapper.setNoticeUndo(noticeId);
		//删掉所有通知
		noticeManMapper.delNoticeManByNotice(noticeId);
		return 1;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int getSendNoticeCnt(String schoolId, String title, Long sendManId, int status, int progress, String beginTime,
			String endTime) throws Exception {
		// TODO Auto-generated method stub
		String beginDate = CommonTool.formatDate(beginTime) + " 00:00:00";
		String endDate = CommonTool.formatDate(endTime) + " 23:59:59";
		String statusVal = "-1";
		if((status == 0) && (progress == 1)){
			statusVal ="1";
		} else if((status == 0) && (progress == 2)){
			statusVal = "2";
		} else if((status == 0) && (progress == 3)){
			statusVal = "3";
		} else if((status == 0) && (progress == 4)){
			statusVal = "4";
		} else if((status == 1) && (progress == 0)){
			statusVal = "1,2,3";
		} else if((status == 1) && (progress == 1)){
			statusVal = "1";
		} else if((status == 1) && (progress == 2)){
			statusVal = "2";
		} else if((status == 1) && (progress == 3)){
			statusVal = "3";
		} else if((status == 1) && (progress == 4)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 0)){
			statusVal = "4"; 
		} else if((status == 2) && (progress == 1)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 2)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 3)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 4)){
			statusVal = "4";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schoolId", schoolId);
		params.put("title", title);
		params.put("sendMan", sendManId);
		params.put("status", statusVal);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		return noticeMapper.getSendNoticeCnt(params);
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public List<NoticeVO> getSendNotice(String schoolId, String title, Long sendManId, int status, int progress, String beginTime,
			String endTime, int pageIndex, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		List<NoticeVO> noticeVOs = new ArrayList<NoticeVO>();
		
		String beginDate = CommonTool.formatDate(beginTime) + " 00:00:00";
		String endDate = CommonTool.formatDate(endTime) + " 23:59:59";
		String statusVal = "-1";
		if((status == 0) && (progress == 1)){
			statusVal ="1";
		} else if((status == 0) && (progress == 2)){
			statusVal = "2";
		} else if((status == 0) && (progress == 3)){
			statusVal = "3";
		} else if((status == 0) && (progress == 4)){
			statusVal = "4";
		} else if((status == 1) && (progress == 0)){
			statusVal = "1,2,3";
		} else if((status == 1) && (progress == 1)){
			statusVal = "1";
		} else if((status == 1) && (progress == 2)){
			statusVal = "2";
		} else if((status == 1) && (progress == 3)){
			statusVal = "3";
		} else if((status == 1) && (progress == 4)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 0)){
			statusVal = "4"; 
		} else if((status == 2) && (progress == 1)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 2)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 3)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 4)){
			statusVal = "4";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schoolId", schoolId);
		params.put("title", title);
		params.put("sendMan", sendManId);
		params.put("status", statusVal);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("pageIndex", pageIndex);
		params.put("pageSize", pageSize);
		
		List<Notice> notices = noticeMapper.getSendNotice(params);
		for(Notice notice: notices){
			NoticeVO noticeVO = new NoticeVO();
			noticeVO.setNoticeid(notice.getTabid());
			noticeVO.setNoticetitle(notice.getNoticetitle());
			noticeVO.setSendmanname(notice.getSendmanname());
			noticeVO.setSendmanpic(notice.getSendmanpic());
			noticeVO.setSendtime(notice.getSendtime());
			switch(notice.getProgress().intValue()){
			case 1:{
				noticeVO.setNoticestatus("发出");
				noticeVO.setNoticeprogress("新建/撤销");
				break;
			}
			case 2:{
				noticeVO.setNoticestatus("发出");
				noticeVO.setNoticeprogress("处理中");
				break;
			}
			case 3:{
				noticeVO.setNoticestatus("发出");
				noticeVO.setNoticeprogress("阅毕");
				break;
			}
			case 4:{
				noticeVO.setNoticestatus("撤销");
				noticeVO.setNoticeprogress("已撤销");
				break;
			}
			}
			
			noticeVOs.add(noticeVO);
		}
		return noticeVOs;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int getReceiveNoticeCnt(String schoolId, Long receiveManId, String title, String sendMan, int status,
			int progress, String beginTime, String endTime) throws Exception {
		// TODO Auto-generated method stub
		String beginDate = CommonTool.formatDate(beginTime) + " 00:00:00";
		String endDate = CommonTool.formatDate(endTime) + " 23:59:59";
		int statusVal = -1;
		if(progress == 1){
			statusVal = 1;
		} else if(progress == 2){
			statusVal = 2;
		} else if(progress == 3){
			statusVal = 3;
		}
		
		int readVal = -1;
		if(status == 1){
			readVal = 1;
		} else if (status == 2){
			readVal = 0;
		} else if (status == 3){
			readVal = 2;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schoolId", schoolId);
		params.put("receiveManId", receiveManId);
		params.put("title", title);
		params.put("sendMan", sendMan);
		params.put("status", statusVal);
		params.put("readStatus", readVal);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		return noticeMapper.getReceiveNoticeCnt(params);
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public List<NoticeManVO> getReceiveNotice(String schoolId, Long receiveManId, String title, String sendMan,
			int status, int progress, String beginTime, String endTime, int pageIndex, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		List<NoticeManVO> noticeManVOs = new ArrayList<NoticeManVO>();
		
		String beginDate = CommonTool.formatDate(beginTime) + " 00:00:00";
		String endDate = CommonTool.formatDate(endTime) + " 23:59:59";
		int statusVal = -1;
		if(progress == 1){
			statusVal = 1;
		} else if(progress == 2){
			statusVal = 2;
		} else if(progress == 3){
			statusVal = 3;
		}
		
		int readVal = -1;
		if(status == 1){
			readVal = 1;
		} else if (status == 2){
			readVal = 0;
		} else if (status == 3){
			readVal = 2;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schoolId", schoolId);
		params.put("receiveManId", receiveManId);
		params.put("title", title);
		params.put("sendMan", sendMan);
		params.put("status", statusVal);
		params.put("readStatus", readVal);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("pageIndex", pageIndex);
		params.put("pageSize", pageSize);
		
		List<NoticeAndNoticeManVO> noticeMans = noticeMapper.getReceiveNotice(params);
		for(NoticeAndNoticeManVO noticeMan: noticeMans){
			NoticeManVO noticeManVO = new NoticeManVO();
			noticeManVO.setNoticemaid(noticeMan.getNoticemanid());
			noticeManVO.setNoticeid(noticeMan.getNoticeid());
			noticeManVO.setSendmanname(noticeMan.getSendmanname());
			noticeManVO.setSendmanpic(noticeMan.getSendmanpic());
			noticeManVO.setNoticetitle(noticeMan.getNoticetitle());
			noticeManVO.setSendtime(noticeMan.getSendtime());
			noticeManVO.setReadtime(noticeMan.getReadtime());
			switch(noticeMan.getReadstatus().intValue()){
			case 1:{
				noticeManVO.setNoticestatus("已阅");
				break;
			}
			case 0:{
				noticeManVO.setNoticestatus("未阅");
				break;
			}
			case 2:{
				noticeManVO.setNoticestatus("已回复");
				break;
			}
			}
			switch(noticeMan.getProgress().intValue()){
			case 1:{
				noticeManVO.setNoticeprogress("新建");
				break;
			}
			case 2:{
				noticeManVO.setNoticeprogress("处理中");
				break;
			}
			case 3:{
				noticeManVO.setNoticeprogress("阅毕");
				break;
			}
			}
			
			noticeManVOs.add(noticeManVO);
		}
		
		return noticeManVOs;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public NoticeDetailVO getNoticeById(int noticeId) throws Exception {
		// TODO Auto-generated method stub
		NoticeDetailVO noticeDetail = new NoticeDetailVO();
		
		//先取通知内容
		Notice notice = new Notice();
		notice = noticeMapper.selectByPrimaryKey(noticeId);
		//再取阅读情况
		List<NoticeMan> readMans = noticeManMapper.getReadList(noticeId);
		int readCnt = noticeManMapper.getNoticeReadCntById(noticeId, 1);
		readCnt = readCnt + noticeManMapper.getNoticeReadCntById(noticeId, 2);
		int noReadCnt = noticeManMapper.getNoticeReadCntById(noticeId, 0);
		
		noticeDetail.setNoticeid(notice.getTabid());
		noticeDetail.setNoticetitle(notice.getNoticetitle());
		noticeDetail.setStatus(notice.getProgress());
		noticeDetail.setSendmanid(notice.getSendmanid());
		noticeDetail.setSendmanname(notice.getSendmanname());
		noticeDetail.setSendmanpic(notice.getSendmanpic());
		noticeDetail.setSendtime(notice.getSendtime());
		noticeDetail.setNoticecontent(notice.getNoticecontent());
		noticeDetail.setNoticeencname(notice.getNoticeencname());
		noticeDetail.setNoticeencaddr(notice.getNoticeencaddr());		
		noticeDetail.setReadcnt(readCnt);
		noticeDetail.setNoreadcnt(noReadCnt);
		noticeDetail.setReadMans(readMans);
		
		return noticeDetail;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int setNoticeRead(int noticeId, Long receiveManId) throws Exception {
		// TODO Auto-generated method stub
		NoticeMan record = new NoticeMan();
		record.setNoticeid(noticeId);
		record.setReceivemanid(receiveManId);
		noticeManMapper.setNoticeRead(record);
		//如果已经全部人都阅读，则设为阅毕，不然设为处理中
		if(noticeManMapper.getNoticeNoReadCntById(noticeId) == 0){
			noticeMapper.setNoticeStatus(noticeId, 3);
		} else {
			noticeMapper.setNoticeStatus(noticeId, 2);
		}
		
		return 1;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int getNoticeReadCntById(int noticeId) throws Exception {
		// TODO Auto-generated method stub
		int cnt = noticeManMapper.getNoticeReadCntById(noticeId, 1) + noticeManMapper.getNoticeReadCntById(noticeId, 2);
		return cnt;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int delNotices(List<Integer> noticeIds, int status) throws Exception {
		// TODO Auto-generated method stub
		String noticeIdStr = "";
		for (int noticeId : noticeIds) {
			noticeIdStr = noticeIdStr + "" + noticeId + ",";
		}
		if (noticeIdStr.indexOf(",") != -1) {
			noticeIdStr = noticeIdStr.substring(0, noticeIdStr.length() - 1);
		}

		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("noticeIds", noticeIdStr);
		params.put("status", status);
		noticeMapper.delNotice(params);
		return 1;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int getNoticeReadCntByMan(int noticeId, Long receiveManId) throws Exception {
		// TODO Auto-generated method stub
		return noticeManMapper.getNoticeReadCntByMan(noticeId, receiveManId);
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int getAllNoticeCnt(String schoolId, String title, String sendMan, String beginTime, String endTime, int status)
			throws Exception {
		// TODO Auto-generated method stub
		String beginDate = CommonTool.formatDate(beginTime) + " 00:00:00";
		String endDate = CommonTool.formatDate(endTime) + " 23:59:59";
		String statusVal = "-1";
		if(status == 1){
			statusVal = "1";
		} else if(status == 2){
			statusVal = "0";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schoolId", schoolId);
		params.put("title", title);
		params.put("sendMan", sendMan);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("status", statusVal);
		return noticeMapper.getAllNoticeCnt(params);
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public List<NoticeVO> getAllNotice(String schoolId, String title, String sendMan, String beginTime, String endTime,
			int pageIndex, int pageSize, int status) throws Exception {
		// TODO Auto-generated method stub
		String beginDate = CommonTool.formatDate(beginTime) + " 00:00:00";
		String endDate = CommonTool.formatDate(endTime) + " 23:59:59";
		String statusVal = "-1";
		if(status == 1){
			statusVal = "1";
		} else if(status == 2){
			statusVal = "0";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schoolId", schoolId);
		params.put("title", title);
		params.put("sendMan", sendMan);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("pageIndex", pageIndex);
		params.put("pageSize", pageSize);
		params.put("status", statusVal);
		return noticeMapper.getAllNotice(params);
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public NoticeManDetailVO getNoticeByReceiveId(int noticeManTid) throws Exception {
		// TODO Auto-generated method stub
		NoticeManDetailVO noticeDetail = new NoticeManDetailVO();
		int noticeId = noticeManMapper.getNoticeIdById(noticeManTid);
		//先取通知内容
		Notice notice = new Notice();
		notice = noticeMapper.selectByPrimaryKey(noticeId);
		//再取阅读情况
		List<NoticeMan> readMans = noticeManMapper.getReadList(noticeId);
		int readCnt = noticeManMapper.getNoticeReadCntById(noticeId, 1);
		readCnt = readCnt + noticeManMapper.getNoticeReadCntById(noticeId, 2);
		int noReadCnt = noticeManMapper.getNoticeReadCntById(noticeId, 0);
		//取阅读状态
		NoticeMan noticeMan  = noticeManMapper.selectByPrimaryKey(noticeManTid);
		
		noticeDetail.setNoticeid(notice.getTabid());
		noticeDetail.setNoticetitle(notice.getNoticetitle());
		noticeDetail.setStatus(notice.getProgress());
		noticeDetail.setSendmanid(notice.getSendmanid());
		noticeDetail.setSendmanname(notice.getSendmanname());
		noticeDetail.setSendmanpic(notice.getSendmanpic());
		noticeDetail.setSendtime(notice.getSendtime());
		noticeDetail.setNoticecontent(notice.getNoticecontent());
		noticeDetail.setNoticeencname(notice.getNoticeencname());
		noticeDetail.setNoticeencaddr(notice.getNoticeencaddr());		
		noticeDetail.setReadcnt(readCnt);
		noticeDetail.setNoreadcnt(noReadCnt);
		noticeDetail.setReadMans(readMans);
		noticeDetail.setReadStatus(CommonTool.int2byte(noticeMan.getStatus()));
		noticeDetail.setReplycontent(noticeMan.getReplycontent());
		
		return noticeDetail;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int getNoReadCntByMan(String schoolId, Long receiveManId) throws Exception {
		// TODO Auto-generated method stub
		return noticeManMapper.getNoticeNoReadCntByMan(schoolId, receiveManId);
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int setNoticeReply(int noticeId, Long receiveManId, String replyContent) throws Exception {
		// TODO Auto-generated method stub
		NoticeMan record = new NoticeMan();
		record.setNoticeid(noticeId);
		record.setReceivemanid(receiveManId);
		record.setReplycontent(replyContent);
		noticeManMapper.setNoticeReply(record);
		return 1;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int realDelNotices(List<Integer> noticeIds) throws Exception {
		// TODO Auto-generated method stub
		String noticeIdStr = "";
		for (int noticeId : noticeIds) {
			noticeIdStr = noticeIdStr + "" + noticeId + ",";
		}
		if (noticeIdStr.indexOf(",") != -1) {
			noticeIdStr = noticeIdStr.substring(0, noticeIdStr.length() - 1);
		}

		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("noticeIds", noticeIdStr);
		
		noticeMapper.realDelNotices(params); //删除通知公告信息
		noticeManMapper.realDelNoticeMans(params); //删除通知公告通知的人
		return 1;
	}

}
