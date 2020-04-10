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

package com.goldeneyes.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goldeneyes.pojo.NoticeMan;
import com.goldeneyes.service.AffairApproveService;
import com.goldeneyes.service.NoticeService;
import com.goldeneyes.util.CommonTool;
import com.goldeneyes.util.PushUtil;
import com.goldeneyes.vo.AffairApplyVO;
import com.goldeneyes.vo.NoticeDetailVO;
import com.goldeneyes.vo.NoticeManDetailVO;
import com.goldeneyes.vo.NoticeManVO;
import com.goldeneyes.vo.NoticeVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Resource
	NoticeService noticeService;
	@Resource
	AffairApproveService affairApproveService;
	
	/**
	 * 7.新增通知公告
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/addNotice")
	public void addNotice(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("schoolId") || !jsonInput.has("noticeTitle") || !jsonInput.has("noticeContent")
				|| !jsonInput.has("noticEncName") || !jsonInput.has("noticeEncAddr") || !jsonInput.has("smsSync")
				|| !jsonInput.has("sendManId") || !jsonInput.has("sendManCode") || !jsonInput.has("sendManName") 
				|| !jsonInput.has("receiveManIds") || !jsonInput.has("receiveManCodes") || !jsonInput.has("receiveManNames")
				|| !jsonInput.has("sendManPic") || !jsonInput.has("receiveManPics")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			String schoolId = "";
			String noticeTitle = "";
			String noticeContent = "";
			String noticEncName = "";
			String noticeEncAddr = "";
			int smsSync = 0;
			Long sendManId = 0l;
			String sendManCode = "";
			String sendManName = "";
			String sendManPic = "";
			List<Long> receiveManIds = new ArrayList<Long>();
			List<String> receiveManCodes = new ArrayList<String>();
			List<String> receiveManNames = new ArrayList<String>();
			List<String> receiveManPics = new ArrayList<String>();
			try {
				schoolId = jsonInput.getString("schoolId");
				noticeTitle = jsonInput.getString("noticeTitle");
				noticeContent = jsonInput.getString("noticeContent");
				noticEncName = jsonInput.getString("noticEncName");
				noticeEncAddr = jsonInput.getString("noticeEncAddr");
				smsSync = Integer.parseInt(jsonInput.getString("smsSync"));
				sendManId = Long.parseLong(jsonInput.getString("sendManId"));
				sendManCode = jsonInput.getString("sendManCode");
				sendManName = jsonInput.getString("sendManName");
				sendManPic = jsonInput.getString("sendManPic");
				receiveManIds = CommonTool.getListLongFromJsonArray(jsonInput.getJSONArray("receiveManIds"));
				receiveManCodes = CommonTool.getListFromJsonArray(jsonInput.getJSONArray("receiveManCodes"));
				receiveManNames = CommonTool.getListFromJsonArray(jsonInput.getJSONArray("receiveManNames"));
				receiveManPics = CommonTool.getListFromJsonArray(jsonInput.getJSONArray("receiveManPics"));
			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}

			int success = 0;
			try {	
				success = noticeService.addNotice(schoolId, noticeTitle, noticeContent, noticEncName, noticeEncAddr
						, smsSync, sendManId,sendManCode, sendManName, sendManPic, receiveManIds,receiveManCodes, receiveManNames, receiveManPics);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}
			if (success == 0) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1002").toString());				
			} else {
				jsonData.put("Result", success);
				//成功后发送推送
				for(Long receiveManId: receiveManIds){
					PushMsg pushMsg = new PushMsg(schoolId, receiveManId, noticeTitle, noticeContent);
					Thread thread = new Thread(pushMsg);
					thread.start();
				}
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			}
		}
	}
	
	private class PushMsg implements Runnable {
		private String schoolId;
		private String noticeTitle;
		private String noticeContent;
		private Long receiveManId;
		
		public PushMsg(String schoolId, Long receiveManId, String noticeTitle, String noticeContent){
			this.schoolId = schoolId;
			this.receiveManId = receiveManId;
			this.noticeTitle = noticeTitle;
			this.noticeContent = noticeContent;
		}

		/**
		 *  @author Administrator
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int cnt = 0;
			try {
				cnt = noticeService.getNoReadCntByMan(schoolId, receiveManId) + affairApproveService.getNoApproveCntByMan(schoolId, receiveManId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PushUtil.pushMsg(receiveManId, noticeTitle, noticeContent, cnt);
		}
	}

	/**
	 * 8.撤销通知公告
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/setNoticeUndo")
	public void setNoticeUndo(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("noticeId")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			int noticeId = 0;

			try {
				noticeId = Integer.parseInt(jsonInput.getString("noticeId"));
			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}

			int success = 0;
			try {
				// 先判断是否已被阅读
				if (noticeService.getNoticeReadCntById(noticeId) > 0) {
					CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1014").toString());
					return;
				} else {
					success = noticeService.setNoticeUndo(noticeId);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}
			if (success == 0) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1002").toString());
			} else {
				jsonData.put("Result", success);
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			}
		}
	}
	
	/**
	 * 9.阅读通知公告
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/setNoticeRead")
	public void setNoticeRead(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("noticeId") || !jsonInput.has("receiveManId")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			int noticeId = 0;
			Long receiveManId = 0l;
			String replyContent = "";
			
			try {
				noticeId = Integer.parseInt(jsonInput.getString("noticeId"));
				receiveManId = Long.parseLong(jsonInput.getString("receiveManId"));
			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}

			int success = 0;
			try {
//				if(noticeService.getNoticeReadCntByMan(noticeId, receiveManId) == 0){
					success = noticeService.setNoticeRead(noticeId, receiveManId);
//				} else {
//					success = 1;
//				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}
			if (success == 0) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1002").toString());
			} else {
				jsonData.put("Result", success);
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			}
		}
	}

	/**
	 * 10.获取发送的通知公告列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getSendNotice")
	public void getSendNotice(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("schoolId") || !jsonInput.has("title") || !jsonInput.has("status") 
				|| !jsonInput.has("progress") || !jsonInput.has("beginTime") || !jsonInput.has("endTime") 
				|| !jsonInput.has("pageIndex") || !jsonInput.has("pageSize") || !jsonInput.has("sendManId")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			String schoolId = "";
			String title = "";
			Long sendManId = 0l;
			int status = 0;
			int progress = 0;
			String beginTime = "";
			String endTime = "";
			int pageIndex = 0;
			int pageSize = 0;
			
			try {
				schoolId = jsonInput.getString("schoolId");
				title = jsonInput.getString("title");
				sendManId = Long.parseLong(jsonInput.getString("sendManId"));
				status = Integer.parseInt(jsonInput.getString("status"));
				progress = Integer.parseInt(jsonInput.getString("progress"));
				beginTime = jsonInput.getString("beginTime");
				endTime = jsonInput.getString("endTime");
				pageIndex = Integer.parseInt(jsonInput.getString("pageIndex"));
				pageSize = Integer.parseInt(jsonInput.getString("pageSize"));
			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}
			if ((pageIndex <= 0) || (pageSize < 0)) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1006").toString());
				return;
			}
			int totalCnt = 0;
			int totalPage = 0;
			List<NoticeVO> notices = new ArrayList<NoticeVO>();
			try {
				totalCnt = noticeService.getSendNoticeCnt(schoolId, title, sendManId, status, progress, beginTime, endTime);
				totalPage = CommonTool.getTotalPage(totalCnt, pageSize);
				if (pageSize == 0) {
					notices = noticeService.getSendNotice(schoolId, title, sendManId, status, progress, beginTime, endTime, 1, totalCnt);
				} else {
					notices = noticeService.getSendNotice(schoolId, title, sendManId, status, progress, beginTime, endTime, pageIndex, pageSize);
				}
				JSONArray jsonArray = new JSONArray();
				for (NoticeVO notice : notices) {
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("NoticeId", notice.getNoticeid());
					jsonobj.put("NoticeTitle", notice.getNoticetitle());
					jsonobj.put("NoticeStatus", notice.getNoticestatus());
					jsonobj.put("NoticeProgress", notice.getNoticeprogress());
					jsonobj.put("SendManName", notice.getSendmanname());
					jsonobj.put("SendManPic", notice.getSendmanpic());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					jsonobj.put("SendTime", sdf.format(notice.getSendtime()));
					jsonArray.put(jsonobj);
				}
				jsonData.put("TotalCnt", totalCnt);
				jsonData.put("TotalPage", totalPage);
				jsonData.put("Data", jsonArray);
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}

		}
	}
	
	/**
	 * 11.获取收到的通知公告列表（接收人为单人）
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getReceiveNotice")
	public void getReceiveNotice(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("schoolId") || !jsonInput.has("receiveManId") || !jsonInput.has("title") 
				|| !jsonInput.has("sendMan") || !jsonInput.has("status") 
				|| !jsonInput.has("progress") || !jsonInput.has("beginTime") || !jsonInput.has("endTime") 
				|| !jsonInput.has("pageIndex") || !jsonInput.has("pageSize")
				 ) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			String schoolId = "";
			Long receiveManId = 0l;
			String title = "";
			String sendMan = "";
			int status = 0;
			int progress = 0;
			String beginTime = "";
			String endTime = "";
			int pageIndex = 0;
			int pageSize = 0;
			
			try {
				schoolId = jsonInput.getString("schoolId");
				receiveManId = Long.parseLong(jsonInput.getString("receiveManId"));
				title = jsonInput.getString("title");
				sendMan = jsonInput.getString("sendMan");
				status = Integer.parseInt(jsonInput.getString("status"));
				progress = Integer.parseInt(jsonInput.getString("progress"));
				beginTime = jsonInput.getString("beginTime");
				endTime = jsonInput.getString("endTime");
				pageIndex = Integer.parseInt(jsonInput.getString("pageIndex"));
				pageSize = Integer.parseInt(jsonInput.getString("pageSize"));
			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}
			if ((pageIndex <= 0) || (pageSize < 0)) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1006").toString());
				return;
			}
			int totalCnt = 0;
			int totalPage = 0;
			List<NoticeManVO> noticeMans = new ArrayList<NoticeManVO>();
			try {
				totalCnt = noticeService.getReceiveNoticeCnt(schoolId, receiveManId, title, sendMan, status, progress, beginTime, endTime);
				totalPage = CommonTool.getTotalPage(totalCnt, pageSize);
				if (pageSize == 0) {
					noticeMans = noticeService.getReceiveNotice(schoolId, receiveManId, title, sendMan, status, progress, beginTime, endTime, 1, totalCnt);
				} else {
					noticeMans = noticeService.getReceiveNotice(schoolId, receiveManId, title, sendMan, status, progress, beginTime, endTime, pageIndex, pageSize);
				}
				JSONArray jsonArray = new JSONArray();
				for (NoticeManVO noticeMan : noticeMans) {
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("NoticeManId", noticeMan.getNoticemaid());
					jsonobj.put("NoticeId", noticeMan.getNoticeid());
					jsonobj.put("SendManName", noticeMan.getSendmanname());
					jsonobj.put("SendManPic", noticeMan.getSendmanpic());
					jsonobj.put("NoticeTitle", noticeMan.getNoticetitle());
					jsonobj.put("NoticeStatus", noticeMan.getNoticestatus());
					jsonobj.put("NoticeProgress", noticeMan.getNoticeprogress());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					jsonobj.put("SendTime", sdf.format(noticeMan.getSendtime()));
					if(noticeMan.getReadtime() == null){
						jsonobj.put("ReadTime", "");
					} else {
						jsonobj.put("ReadTime", sdf.format(noticeMan.getReadtime()));
					}
					jsonArray.put(jsonobj);
				}
				jsonData.put("TotalCnt", totalCnt);
				jsonData.put("TotalPage", totalPage);
				jsonData.put("Data", jsonArray);
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}

		}
	}
	
	/**
	 * 12.通过ID获取通知公告
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getNoticeById")
	public void getNoticeById(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("noticeId")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			int noticeId = 0;
			try {
				noticeId = Integer.parseInt(jsonInput.getString("noticeId"));
			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}

			NoticeDetailVO noticeDetail = new NoticeDetailVO();
			try {
				noticeDetail = noticeService.getNoticeById(noticeId);
				
				jsonData.put("NoticeId", noticeDetail.getNoticeid());
				jsonData.put("NoticeTitle", noticeDetail.getNoticetitle());
				jsonData.put("Status", noticeDetail.getStatus());
				jsonData.put("SendManId", String.valueOf(noticeDetail.getSendmanid()));
				jsonData.put("SendManName", noticeDetail.getSendmanname());
				jsonData.put("SendManPic", noticeDetail.getSendmanpic());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonData.put("SendTime", sdf.format(noticeDetail.getSendtime()));
				jsonData.put("NoticeContent", noticeDetail.getNoticecontent());
				jsonData.put("NoticeEncName", noticeDetail.getNoticeencname());
				jsonData.put("NoticeEncAddr", noticeDetail.getNoticeencaddr());
				jsonData.put("ReadCnt", noticeDetail.getReadcnt());
				jsonData.put("NoReadCnt", noticeDetail.getNoreadcnt());
				JSONArray jsonArray = new JSONArray();
				for(NoticeMan readMan: noticeDetail.getReadMans()){
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("ReceiveManName", readMan.getReceivemanname());
					jsonobj.put("ReceiveManPic", readMan.getReceivemanpic());
					if(readMan.getReplycontent() != null){
						jsonobj.put("ReplyContent", readMan.getReplycontent());
					} else {
						jsonobj.put("ReplyContent", "");
					}
					jsonobj.put("IsRead", readMan.getStatus());
					if(readMan.getReadtime() != null){
						jsonobj.put("ReadTime", sdf.format(readMan.getReadtime()));
					} else {
						jsonobj.put("ReadTime", "");
					}
					jsonArray.put(jsonobj);
				}
				jsonData.put("Data", jsonArray);

				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}

		}
	}
	
	/**
	 * 13.删除通知公告
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/delNotices")
	public void delNotices(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("noticeIds") || !jsonInput.has("status")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			List<Integer> noticeIds = new ArrayList<Integer>();
			int status = 0;

			try {
				noticeIds = CommonTool.getListIntFromJsonArray(jsonInput.getJSONArray("noticeIds"));
				status = Integer.parseInt(jsonInput.getString("status"));
			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}

			int success = 0;
			try {
				success = noticeService.delNotices(noticeIds, status);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}
			if (success == 0) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1002").toString());
			} else {
				jsonData.put("Result", success);
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			}
		}
	}
	
	/**
	 * 22.获取全部通知公告列表
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getAllNotice")
	public void getAllNotice(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("schoolId") || !jsonInput.has("title") || !jsonInput.has("sendMan")
				|| !jsonInput.has("beginTime") || !jsonInput.has("endTime") || !jsonInput.has("status")
				|| !jsonInput.has("pageIndex") || !jsonInput.has("pageSize")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			String schoolId = "";
			String title = "";
			String sendMan = "";
			String beginTime = "";
			String endTime = "";
			int status = 0;
			int pageIndex = 0;
			int pageSize = 0;
			
			try {
				schoolId = jsonInput.getString("schoolId");
				title = jsonInput.getString("title");
				sendMan = jsonInput.getString("sendMan");
				beginTime = jsonInput.getString("beginTime");
				endTime = jsonInput.getString("endTime");
				status = Integer.parseInt(jsonInput.getString("status"));
				pageIndex = Integer.parseInt(jsonInput.getString("pageIndex"));
				pageSize = Integer.parseInt(jsonInput.getString("pageSize"));
			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}
			if ((pageIndex <= 0) || (pageSize < 0)) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1006").toString());
				return;
			}
			int totalCnt = 0;
			int totalPage = 0;
			List<NoticeVO> notices = new ArrayList<NoticeVO>();
			try {
				totalCnt = noticeService.getAllNoticeCnt(schoolId, title, sendMan, beginTime, endTime, status);
				totalPage = CommonTool.getTotalPage(totalCnt, pageSize);
				if (pageSize == 0) {
					notices = noticeService.getAllNotice(schoolId, title, sendMan, beginTime, endTime, 1, totalCnt, status);
				} else {
					notices = noticeService.getAllNotice(schoolId, title, sendMan, beginTime, endTime, pageIndex, pageSize, status);
				}
				JSONArray jsonArray = new JSONArray();
				for (NoticeVO notice : notices) {
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("NoticeId", notice.getNoticeid());
					jsonobj.put("NoticeTitle", notice.getNoticetitle());
					jsonobj.put("SendManName", notice.getSendmanname());
					jsonobj.put("SendManPic", notice.getSendmanpic());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					jsonobj.put("CreateTime", sdf.format(notice.getSendtime()));
					jsonobj.put("Status", notice.getNoticestatus());
					jsonArray.put(jsonobj);
				}
				jsonData.put("TotalCnt", totalCnt);
				jsonData.put("TotalPage", totalPage);
				jsonData.put("Data", jsonArray);
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}

		}
	}
	
	/**
	 * 25.通过通知接收表ID获取通知公告
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getNoticeByReceiveId")
	public void getNoticeByReceiveId(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("noticeManId")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			int noticeManTid = 0;
			try {
				noticeManTid = Integer.parseInt(jsonInput.getString("noticeManId"));
			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}

			NoticeManDetailVO noticeDetail = new NoticeManDetailVO();
			try {
				noticeDetail = noticeService.getNoticeByReceiveId(noticeManTid);
				
				jsonData.put("NoticeId", noticeDetail.getNoticeid());
				jsonData.put("NoticeTitle", noticeDetail.getNoticetitle());
				jsonData.put("Status", noticeDetail.getStatus());
				jsonData.put("ReadStatus", noticeDetail.getReadStatus());
				jsonData.put("SendManId", String.valueOf(noticeDetail.getSendmanid()));
				jsonData.put("SendManName", noticeDetail.getSendmanname());
				jsonData.put("SendManPic", noticeDetail.getSendmanpic());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonData.put("SendTime", sdf.format(noticeDetail.getSendtime()));
				jsonData.put("NoticeContent", noticeDetail.getNoticecontent());
				jsonData.put("NoticeEncName", noticeDetail.getNoticeencname());
				jsonData.put("NoticeEncAddr", noticeDetail.getNoticeencaddr());
				jsonData.put("ReadCnt", noticeDetail.getReadcnt());
				jsonData.put("NoReadCnt", noticeDetail.getNoreadcnt());
				if(noticeDetail.getReplycontent() == null){
					jsonData.put("ReplyContent", "");
				} else {
					jsonData.put("ReplyContent", noticeDetail.getReplycontent());
				}
				JSONArray jsonArray = new JSONArray();
				for(NoticeMan readMan: noticeDetail.getReadMans()){
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("ReceiveManName", readMan.getReceivemanname());
					jsonobj.put("ReceiveManPic", readMan.getReceivemanpic());
					if(readMan.getReplycontent() != null){
						jsonobj.put("ReplyContent", readMan.getReplycontent());
					} else {
						jsonobj.put("ReplyContent", "");
					}
					jsonobj.put("IsRead", readMan.getStatus());
					if(readMan.getReadtime() != null){
						jsonobj.put("ReadTime", sdf.format(readMan.getReadtime()));
					} else {
						jsonobj.put("ReadTime", "");
					}
					jsonArray.put(jsonobj);
				}
				jsonData.put("Data", jsonArray);

				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}

		}
	}
	/**
	 * 获取未读数量
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getNoReadCntByMan")
	public void getNoReadCntByMan(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("schoolId") || !jsonInput.has("receiveManId")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			String schoolId = "";
			Long receiveManId = 0l;
			
			try {
				schoolId = jsonInput.getString("schoolId");
				receiveManId = Long.parseLong(jsonInput.getString("receiveManId"));
			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}

			int noReadCnt = 0;
			try {
				noReadCnt = noticeService.getNoReadCntByMan(schoolId, receiveManId);
				
				jsonData.put("NoReadCnt", noReadCnt);
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}

		}
	}

	/**
	 * 28.回复通知公告
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/setNoticeReply")
	public void setNoticeReply(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("noticeId") || !jsonInput.has("receiveManId") || !jsonInput.has("replyContent")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			int noticeId = 0;
			Long receiveManId = 0l;
			String replyContent = "";
			
			try {
				noticeId = Integer.parseInt(jsonInput.getString("noticeId"));
				receiveManId = Long.parseLong(jsonInput.getString("receiveManId"));
				replyContent = jsonInput.getString("replyContent");
			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}

			int success = 0;
			try {
//				if(noticeService.getNoticeReadCntByMan(noticeId, receiveManId) == 0){
					success = noticeService.setNoticeReply(noticeId, receiveManId, replyContent);
//				} else {
//					success = 1;
//				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}
			if (success == 0) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1002").toString());
			} else {
				jsonData.put("Result", success);
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			}
		}
	}
	
	/**
	 * 真实删除通知公告
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/realDelNotices")
	public void realDelNotices(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("noticeIds")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			List<Integer> noticeIds = new ArrayList<Integer>();

			try {
				noticeIds = CommonTool.getListIntFromJsonArray(jsonInput.getJSONArray("noticeIds"));
			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}

			int success = 0;
			try {
				success = noticeService.realDelNotices(noticeIds);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}
			if (success == 0) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1002").toString());
			} else {
				jsonData.put("Result", success);
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			}
		}
	}
}
