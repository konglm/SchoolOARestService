/*----------------------------------------------------------------
 *  Copyright (C) 2017山东金视野教育科技股份有限公司
 * 版权所有。 
 *
 * 文件名：AttendenceController
 * 文件功能描述：流程设置
 *
 * 
 * 创建标识：xubs20180115
 *
 * 修改标识：
 * 修改描述：
 *----------------------------------------------------------------*/
package com.goldeneyes.service;

import java.util.List;

import com.goldeneyes.pojo.WorkFlow;
import com.goldeneyes.pojo.WorkFlowList;

/**
 * 
 * @author Administrator
 *
 */
public interface WorkFlowService {
	/**
	 * 新增流程
	 * 
	 */
	public int addWorkFlow(String schoolId, String flowName, List<Long> approveManIds, List<String> apprvoveManNames,
			String flowNote) throws Exception;

	/**
	 * 修改流程
	 * 
	 */
	public int setWorkFlow(int workFlowId, String flowName, List<Long> approveManIds, List<String> apprvoveManNames,
			String flowNote, int status) throws Exception;

	/**
	 * @param workFlowId
	 * @return
	 * @throws Exception
	 */
	public int deleteWorkFlow(int workFlowId) throws Exception;

	/**
	 * 通过条件获取流程表记录总数
	 * 
	 * @param schoolId
	 * @param flowName
	 * @return
	 */
	int getCountByCondition(String schoolId, String flowName) throws Exception;

	/**
	 * 分页或整页获取流程数据以列表形式
	 * 
	 * @param schoolId
	 * @param flowName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	List<WorkFlow> getWorkFlowDataByPagination(String schoolId, String flowName, int pageIndex, int pageSize)
			throws Exception;
	/**
	 * 通过流程编号获取其下的多个节点
	 * 
	 * @param workFlowId
	 * @return
	 */
	List<WorkFlowList> getWorkFlowListByWorkFlowId(int workFlowId)throws Exception;
	
	/**
	 * 
	 * 获取选择用流程列表
	 * @param schoolId
	 * @return
	 */
	List<WorkFlow> getSelWorkFlowByCondition(String schoolId)throws Exception;
	
	/**
	 * 
	 * 通过学校标识和流程名称查询记录条数
	 * @param schoolId
	 * @param flowName
	 * @return
	 */
	int getWorkFlowCountByCondition(String schoolId,String flowName)throws Exception;
	/**
	 * 通过流程编号查找对应记录的学校编号
	 * @param recordId
	 * @return
	 */
	WorkFlow getSchoolIdByRecordId(int recordId)throws Exception;
	/**
	 * 通过学校标识和流程名称查找记录标识
	 * @param schoolId
	 * @param flowName
	 * @return
	 * @throws Exception
	 */
	WorkFlow getTableRecordByCondition(String schoolId,String flowName)throws Exception;
}
