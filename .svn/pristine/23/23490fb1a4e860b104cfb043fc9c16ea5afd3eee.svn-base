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

import com.goldeneyes.IDao.AffairApplyMapper;
import com.goldeneyes.IDao.AffairApproveMapper;
import com.goldeneyes.pojo.AffairApply;
import com.goldeneyes.pojo.AffairApprove;
import com.goldeneyes.service.AffairApproveService;
import com.goldeneyes.util.CommonTool;
import com.goldeneyes.vo.AffairApplyVO;
import com.goldeneyes.vo.AffairApproveVO;
import com.goldeneyes.vo.ApplyAndApproveVO;

/**
 * @author Administrator
 *
 */
@Service("affairApproveService")
public class AffairApproveServiceImpl implements AffairApproveService {
	@Resource
	AffairApplyMapper affairApplyMapper;
	@Resource
	AffairApproveMapper affairApproveMapper;

	/**
	 *  @author Administrator
	 */
	@Override
	public int addAffairApply(String schoolId, String applyTitle, String applyContent, String applyEncName, String applyEncAddr,
			Long applyManId, String applyManCode, String applyManName, String applyManPic, List<Long> approveManIds,List<String> approveManCodes,
			List<String> approveManNames, List<String> approveManPics) throws Exception {
		// TODO Auto-generated method stub
		AffairApply affairApply = new AffairApply();
		affairApply.setApplycontent(applyContent);
		affairApply.setApplyencaddr(applyEncAddr);
		affairApply.setApplyencname(applyEncName);
		affairApply.setApplymanid(applyManId);
		affairApply.setApplymancode(applyManCode);
		affairApply.setApplymanname(applyManName);
		affairApply.setApplymanpic(applyManPic);
		affairApply.setApplytitle(applyTitle);
		affairApply.setProgress(CommonTool.int2byte(1));
		affairApply.setSchoolid(schoolId);
		affairApply.setSendtime(new Date());
		affairApply.setStatus(CommonTool.int2byte(1));
		affairApplyMapper.addAffairApply(affairApply);
		int maxId = affairApply.getTabid();
		
		//在增加审批
		int upper = 0;
		for(int i = 0; i < approveManIds.size(); i++){
			AffairApprove affairApprove = new AffairApprove();
			affairApprove.setApplyid(maxId);
			affairApprove.setApprovemanid(approveManIds.get(i));
			affairApprove.setApprovemancode(approveManCodes.get(i));
			affairApprove.setApprovemanname(approveManNames.get(i));
			affairApprove.setApprovemanpic(approveManPics.get(i));
			if(upper == 0){ //第一个审批状态为未批
				affairApprove.setStatus(CommonTool.int2byte(0));
			} else { //其余的为等待
				affairApprove.setStatus(CommonTool.int2byte(3));
			}
			affairApprove.setUpperid(upper);			
			affairApproveMapper.addAffairApprove(affairApprove);
			
			upper = affairApprove.getTabid();
		}
		
		return maxId;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int getApplyApproveCntById(int applyId) throws Exception {
		// TODO Auto-generated method stub
		return affairApproveMapper.getApplyApproveCntById(applyId);
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int setAffairApplyUndo(int applyId) throws Exception {
		// TODO Auto-generated method stub
		//撤销申请
		affairApplyMapper.setAffairApplyUndo(applyId);
		//删掉所有审批
		affairApproveMapper.delApproveByApply(applyId);
		return 1;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int setAffairApprove(int approveId, String approveContent, int approveResult) throws Exception {
		// TODO Auto-generated method stub
		//（1）先修改审批
		AffairApprove affairApprove = new AffairApprove();
		affairApprove.setTabid(approveId);
		affairApprove.setApprovecontent(approveContent);
		affairApprove.setApprovetime(new Date());
		affairApprove.setStatus(CommonTool.int2byte(approveResult));
		affairApproveMapper.setAffairApprove(affairApprove);	
		if(approveResult == 1){ //同意的话
			//（2）根据所有的审批判断申请状态，修改申请状态
			if(affairApproveMapper.isApplyFinished(approveId) == 0){ //是否所有的审批都已经完成
				//已完成的话修改申请状态
				affairApplyMapper.setApplyStatus(approveId, 3);
			} else {
				//已经开始审批并且未完成的话修改申请状态
				affairApplyMapper.setApplyStatus(approveId, 2);
				//（3）将上级为自己的审批状态改为未批，其余扔保持等待状态	
				affairApproveMapper.setNextIdStatus(approveId, 0);
			}
		} else { //驳回的话
			//驳回的话修改申请状态
			affairApplyMapper.setApplyStatus(approveId, 4);
			//（3）驳回的话，将其余审批都删除掉	
			affairApproveMapper.delDownId(approveId);
		}
		return 1;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int getAffairApplyCnt(String schoolId, String title, Long applyManId, int status, int progress,
			String beginTime, String endTime) throws Exception {
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
		} else if((status == 0) && (progress == 5)){
			statusVal = "5";
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
		} else if((status == 1) && (progress == 5)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 0)){
			statusVal = "5"; 
		} else if((status == 2) && (progress == 1)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 2)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 3)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 4)){
			statusVal = "-2";
		} else if((status == 2) && (progress == 5)){
			statusVal = "5";
		} else if((status == 3) && (progress == 0)){
			statusVal = "4"; 
		} else if((status == 3) && (progress == 1)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 3) && (progress == 2)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 3) && (progress == 3)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 3) && (progress == 4)){
			statusVal = "4";
		} else if((status == 3) && (progress == 5)){
			statusVal = "-2";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schoolId", schoolId);
		params.put("applyManId", applyManId);
		params.put("title", title);
		params.put("status", statusVal);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		return affairApplyMapper.getAffairApplyCnt(params);
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public List<AffairApplyVO> getAffairApply(String schoolId, String title, Long applyManId, int status,
			int progress, String beginTime, String endTime, int pageIndex, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		List<AffairApplyVO> affairApplyVOs = new ArrayList<AffairApplyVO>();
		
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
		} else if((status == 0) && (progress == 5)){
			statusVal = "5";
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
		} else if((status == 1) && (progress == 5)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 0)){
			statusVal = "5"; 
		} else if((status == 2) && (progress == 1)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 2)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 3)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 2) && (progress == 4)){
			statusVal = "-2";
		} else if((status == 2) && (progress == 5)){
			statusVal = "5";
		} else if((status == 3) && (progress == 0)){
			statusVal = "4"; 
		} else if((status == 3) && (progress == 1)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 3) && (progress == 2)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 3) && (progress == 3)){
			statusVal = "-2"; //无效，查不出数据
		} else if((status == 3) && (progress == 4)){
			statusVal = "4";
		} else if((status == 3) && (progress == 5)){
			statusVal = "-2";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schoolId", schoolId);
		params.put("applyManId", applyManId);
		params.put("title", title);
		params.put("status", statusVal);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("pageIndex", pageIndex);
		params.put("pageSize", pageSize);
		
		List<AffairApply> affairApplys = affairApplyMapper.getAffairApply(params);
		for(AffairApply affairApply: affairApplys){
			AffairApplyVO affairApplyVO = new AffairApplyVO();
			affairApplyVO.setTabid(affairApply.getTabid());
			affairApplyVO.setApplytitle(affairApply.getApplytitle());
			affairApplyVO.setApplymanname(affairApply.getApplymanname());
			affairApplyVO.setApplymanpic(affairApply.getApplymanpic());
			affairApplyVO.setSendtime(affairApply.getSendtime());
			switch(affairApply.getProgress().intValue()){
			case 1:{
				affairApplyVO.setStatus("发出");
				affairApplyVO.setProgress("新建/撤销");
				break;
			}
			case 2:{
				affairApplyVO.setStatus("发出");
				affairApplyVO.setProgress("审批中");
				break;
			}
			case 3:{
				affairApplyVO.setStatus("发出");
				affairApplyVO.setProgress("审毕");
				break;
			}
			case 4:{
				affairApplyVO.setStatus("被退回");
				affairApplyVO.setProgress("退回");
				break;
			}
			case 5:{
				affairApplyVO.setStatus("撤销");
				affairApplyVO.setProgress("已撤销");
				break;
			}
			}
			
			affairApplyVOs.add(affairApplyVO);
		}
		return affairApplyVOs;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int getAffairApproveCnt(String schoolId, Long approveManId, String title, String applyMan, int status,
			int progress, String beginTime, String endTime) throws Exception {
		// TODO Auto-generated method stub
		String beginDate = CommonTool.formatDate(beginTime) + " 00:00:00";
		String endDate = CommonTool.formatDate(endTime) + " 23:59:59";
		String statusVal = "-1";
		if(status == 1){
			statusVal = "1,2";
		} else if(status == 2){
			statusVal = "3";
		} else if(status == 3){
			statusVal = "0";
		}
		
		String progressVal = "-1";
		if(progress == 1){
			progressVal = "1";
		} else if(progress == 2){
			progressVal = "2";
		} else if(progress == 3){
			progressVal = "3,4";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schoolId", schoolId);
		params.put("approveManId", approveManId);
		params.put("title", title);
		params.put("applyMan", applyMan);
		params.put("status", statusVal);
		params.put("progress", progressVal);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);		
		
		return affairApproveMapper.getAffairApproveCnt(params);
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public List<AffairApproveVO> getAffairApprove(String schoolId, Long approveManId, String title, String applyMan,
			int status, int progress, String beginTime, String endTime, int pageIndex, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		List<AffairApproveVO> affairApproves = new ArrayList<AffairApproveVO>();
		String beginDate = CommonTool.formatDate(beginTime) + " 00:00:00";
		String endDate = CommonTool.formatDate(endTime) + " 23:59:59";
		String statusVal = "-1";
		if(status == 1){
			statusVal = "1,2";
		} else if(status == 2){
			statusVal = "3";
		} else if(status == 3){
			statusVal = "0";
		}
		
		String progressVal = "-1";
		if(progress == 1){
			progressVal = "1";
		} else if(progress == 2){
			progressVal = "2";
		} else if(progress == 3){
			progressVal = "3,4";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schoolId", schoolId);
		params.put("approveManId", approveManId);
		params.put("title", title);
		params.put("applyMan", applyMan);
		params.put("status", statusVal);
		params.put("progress", progressVal);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("pageIndex", pageIndex);
		params.put("pageSize", pageSize);
		List<ApplyAndApproveVO> applyAndApproveVOs = affairApproveMapper.getAffairApprove(params);
		for(ApplyAndApproveVO applyAndApproveVO: applyAndApproveVOs){
			AffairApproveVO affairApproveVO = new AffairApproveVO();
			affairApproveVO.setTabid(applyAndApproveVO.getApproveid());
			affairApproveVO.setApplyid(applyAndApproveVO.getApplyid());
			affairApproveVO.setApplymanname(applyAndApproveVO.getApplymanname());
			affairApproveVO.setApplymanpic(applyAndApproveVO.getApplymanpic());
			affairApproveVO.setApplytitle(applyAndApproveVO.getApplytitle());
			affairApproveVO.setApprovetime(applyAndApproveVO.getApprovetime());			
			affairApproveVO.setSendtime(applyAndApproveVO.getSendtime());
			switch(applyAndApproveVO.getStatus().intValue()){
			case 0:{
				affairApproveVO.setStatus("未批");
				break;
			}
			case 1:{
				affairApproveVO.setStatus("已批");
				break;
			}
			case 2:{
				affairApproveVO.setStatus("已批");
				break;
			}
			case 3:{
				affairApproveVO.setStatus("等待");
				break;
			}
			}
			switch(applyAndApproveVO.getProgress().intValue()){
			case 1:{
				affairApproveVO.setProgress("新建");
				break;
			}
			case 2:{
				affairApproveVO.setProgress("审批中");
				break;
			}
			case 3:{
				affairApproveVO.setProgress("审毕");
				break;
			}
			case 4:{
				affairApproveVO.setProgress("审毕");
				break;
			}
			}
			affairApproves.add(affairApproveVO);
		}
		return affairApproves;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public AffairApply getAffairApplyById(int applyId) throws Exception {
		// TODO Auto-generated method stub
		return affairApplyMapper.selectByPrimaryKey(applyId);
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public List<AffairApprove> getAffairApproveByApply(int applyId) throws Exception {
		// TODO Auto-generated method stub
		return affairApproveMapper.getAffairApproveByApply(applyId);
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int delAffairApplys(List<Integer> applyIds, int status) throws Exception {
		// TODO Auto-generated method stub
		String applyIdStr = "";
		for (int applyId : applyIds) {
			applyIdStr = applyIdStr + "" + applyId + ",";
		}
		if (applyIdStr.indexOf(",") != -1) {
			applyIdStr = applyIdStr.substring(0, applyIdStr.length() - 1);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("applyIds", applyIdStr);
		params.put("status", status);
		affairApplyMapper.delAffairApplys(params);
		return 1;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int getAllAffairApplyCnt(String schoolId, String title, String applyMan, String beginTime, String endTime, int status)
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
		params.put("applyMan", applyMan);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("status", statusVal);
		return affairApplyMapper.getAllAffairApplyCnt(params);
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public List<AffairApplyVO> getAllAffairApply(String schoolId, String title, String applyMan, String beginTime,
			String endTime, int pageIndex, int pageSize, int status) throws Exception {
		// TODO Auto-generated method stub
		List<AffairApplyVO> affairApplyVOs = new ArrayList<AffairApplyVO>();
		
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
		params.put("applyMan", applyMan);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("pageIndex", pageIndex);
		params.put("pageSize", pageSize);
		params.put("status", statusVal);
		List<AffairApply> affairApplys = affairApplyMapper.getAllAffairApply(params);
		for(AffairApply affairApply: affairApplys){
			AffairApplyVO affairApplyVO = new AffairApplyVO();
			affairApplyVO.setTabid(affairApply.getTabid());
			affairApplyVO.setApplymanname(affairApply.getApplymanname());
			affairApplyVO.setApplymanpic(affairApply.getApplymanpic());
			affairApplyVO.setApplytitle(affairApply.getApplytitle());
			affairApplyVO.setSendtime(affairApply.getSendtime());
			affairApplyVO.setStatus(affairApply.getStatus().toString());
			
			affairApplyVOs.add(affairApplyVO);
		}
		
		return affairApplyVOs;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public ApplyAndApproveVO getAffairApproveById(int approveId) throws Exception {
		// TODO Auto-generated method stub
		return affairApproveMapper.getAffairApproveById(approveId);
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int getNoApproveCntByMan(String schoolId, Long approveManId) throws Exception {
		// TODO Auto-generated method stub
		return affairApproveMapper.getNoApproveCntByMan(schoolId, approveManId);
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public int realDelAffairApplys(List<Integer> applyIds) throws Exception {
		// TODO Auto-generated method stub
		String applyIdStr = "";
		for (int applyId : applyIds) {
			applyIdStr = applyIdStr + "" + applyId + ",";
		}
		if (applyIdStr.indexOf(",") != -1) {
			applyIdStr = applyIdStr.substring(0, applyIdStr.length() - 1);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("applyIds", applyIdStr);

		affairApplyMapper.realDelAffairApplys(params); //删除申请
		affairApproveMapper.realDelAffairApproves(params); //删除审批
		return 1;
	}

}
