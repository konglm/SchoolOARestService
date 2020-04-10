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

import com.goldeneyes.pojo.AffairApply;
import com.goldeneyes.pojo.AffairApprove;
import com.goldeneyes.vo.AffairApplyVO;
import com.goldeneyes.vo.AffairApproveVO;
import com.goldeneyes.vo.ApplyAndApproveVO;

/**
 * @author Administrator
 *
 */
public interface AffairApproveService {
	/**
	 * 新增申请
	 * @param schoolId
	 * @param applyTitle
	 * @param applyContent
	 * @param applyEncName
	 * @param applyEncAddr
	 * @param applyManId
	 * @param applyManName
	 * @param approveManIds
	 * @param approveManNames
	 * @return
	 * @throws Exception
	 */
	public int addAffairApply(String schoolId, String applyTitle, String applyContent, String applyEncName, String applyEncAddr,
			Long applyManId, String applyManCode, String applyManName, String applyManPic, List<Long> approveManIds,List<String> approveManCodes,
			List<String> approveManNames, List<String> approveManPics)
			throws Exception;
	/**
	 * 申请是否已被审批
	 * @param applyId
	 * @return
	 * @throws Exception
	 */
	public int getApplyApproveCntById(int applyId) throws Exception;
	/**
	 * 撤销申请
	 * @param applyId
	 * @return
	 * @throws Exception
	 */
	public int setAffairApplyUndo(int applyId) throws Exception;
	/**
	 * 审批申请
	 * @param approveId
	 * @param approveContent
	 * @param approveResult
	 * @return
	 * @throws Exception
	 */
	public int setAffairApprove(int approveId, String approveContent, int approveResult) throws Exception;
	/**
	 * 获取某人申请数量
	 * @param schoolId
	 * @param title
	 * @param applyManId
	 * @param status
	 * @param progress
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public int getAffairApplyCnt(String schoolId, String title, Long applyManId, int status, int progress, String beginTime, String endTime)
			throws Exception;
	/**
	 * 获取某人申请列表
	 * @param schoolId
	 * @param title
	 * @param applyManId
	 * @param status
	 * @param progress
	 * @param beginTime
	 * @param endTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<AffairApplyVO> getAffairApply(String schoolId, String title, Long applyManId, int status, int progress, String beginTime, String endTime, int pageIndex, int pageSize)
			throws Exception;
	/**
	 * 获取审批数量
	 * @param schoolId
	 * @param approveManId
	 * @param title
	 * @param applyMan
	 * @param status
	 * @param progress
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public int getAffairApproveCnt(String schoolId, Long approveManId, String title, String applyMan, int status, int progress, String beginTime, String endTime)
			throws Exception;
	/**
	 * 获取审批列表
	 * @param schoolId
	 * @param approveManId
	 * @param title
	 * @param applyMan
	 * @param status
	 * @param progress
	 * @param beginTime
	 * @param endTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<AffairApproveVO> getAffairApprove(String schoolId, Long approveManId, String title, String applyMan, int status, int progress, String beginTime, String endTime, int pageIndex, int pageSize)
			throws Exception;
	/**
	 * 通过ID获取申请
	 * @param applyId
	 * @return
	 * @throws Exception
	 */
	public AffairApply getAffairApplyById(int applyId) throws Exception;
	/**
	 * 通过申请ID获取审批列表
	 * @param applyId
	 * @return
	 * @throws Exception
	 */
	public List<AffairApprove> getAffairApproveByApply(int applyId) throws Exception;
	/**
	 * 删除事务及文件申请
	 * @param delAffairApplys
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public int delAffairApplys(List<Integer> applyIds, int status) throws Exception;
	/**
	 * 获取全部申请数量
	 * @param schoolId
	 * @param title
	 * @param progress
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public int getAllAffairApplyCnt(String schoolId, String title, String applyMan, String beginTime, String endTime, int status)
			throws Exception;
	/**
	 * 获取全部申请列表
	 * @param schoolId
	 * @param title
	 * @param progress
	 * @param beginTime
	 * @param endTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<AffairApplyVO> getAllAffairApply(String schoolId, String title, String applyMan, String beginTime, String endTime, int pageIndex, int pageSize, int status)
			throws Exception;
	/**
	 * 通过审批ID获取申请和审批数据
	 * @param approveId
	 * @return
	 * @throws Exception
	 */
	public ApplyAndApproveVO getAffairApproveById(int approveId) throws Exception;
	/**
	 * 
	 * @param schoolId
	 * @param approveManId
	 * @return
	 * @throws Exception
	 */
	public int getNoApproveCntByMan(String schoolId, Long approveManId) throws Exception;
	/**
	 * 真实删除申请和审批
	 * @param applyIds
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public int realDelAffairApplys(List<Integer> applyIds) throws Exception;

}
