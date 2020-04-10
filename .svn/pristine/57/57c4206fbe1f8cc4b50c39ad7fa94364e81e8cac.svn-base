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

/**
 * 
 */
package com.goldeneyes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goldeneyes.IDao.WorkFlowListMapper;
import com.goldeneyes.IDao.WorkFlowMapper;
import com.goldeneyes.pojo.WorkFlow;
import com.goldeneyes.pojo.WorkFlowList;
import com.goldeneyes.service.WorkFlowService;
import com.goldeneyes.util.CommonTool;

/**
 * @author Administrator
 *
 */
@Service("workFlowService")
public class WorkFlowServiceImpl implements WorkFlowService {

	@Resource
	WorkFlowMapper workFlowMapper;
	@Resource
	WorkFlowListMapper workFlowListMapper;

	@Override
	public int addWorkFlow(String schoolId, String flowName, List<Long> approveManIds, List<String> apprvoveManNames,
			String flowNote) throws Exception {
		// TODO Auto-generated method stub
		// 添加流程，向流程表和流程链表各插入记录
		// 先插流程表
		WorkFlow record = new WorkFlow();
		record.setSchoolid(schoolId);
		record.setFlowname(flowName);
		record.setFlownote(flowNote);
		record.setCreatetime(new Date());
		record.setUpdatetime(new Date());// 也就是创建时间
		record.setStatus(CommonTool.int2byte(1));
		workFlowMapper.addWorkFlow(record);
		int workFlowMaxId = record.getTabid();
		// 再插入流程链表
		WorkFlowList workFlowList = new WorkFlowList();
		workFlowList.setWorkflowid(workFlowMaxId);
		for (int index = 0; index < approveManIds.size(); index++) {
			// 每个审批人的标识

			workFlowList.setManname(apprvoveManNames.get(index));
			workFlowList.setManid(approveManIds.get(index));
			workFlowList.setOrderid(index + 1);
			workFlowListMapper.addWorkFlowList(workFlowList);
		}

		return workFlowMaxId;
	}

	/**
	 * 修改流程
	 * 
	 */
	@Override
	public int setWorkFlow(int workFlowId, String flowName, List<Long> approveManIds, List<String> apprvoveManNames,
			String flowNote, int status) throws Exception {
		WorkFlow record = new WorkFlow();

		record.setFlowname(flowName);
		record.setFlownote(flowNote);
		record.setTabid(workFlowId);
		record.setUpdatetime(new Date());// 更新时间：操作者此时的时间

		record.setStatus(CommonTool.int2byte(status));
		workFlowMapper.setWorkFlowById(record);
		//先得到改前的人数
		List<WorkFlowList> workFlowLists = new ArrayList<WorkFlowList>();
		workFlowLists = workFlowListMapper.getWorkFlowListByWorkFlowId(workFlowId);
		int rawCount = workFlowLists.size();
		//改后的审批人数
		int modifiedCount = approveManIds.size();
		if(modifiedCount!=rawCount){
			//先删除原来的流程下的所有审批人再新增
			int deleteResult=workFlowListMapper.deleteByWorkFlowId(workFlowId);
			if(deleteResult!=0){
//				System.out.println("````````````````````````");
				// 再插入流程链表
				WorkFlowList workFlowList = new WorkFlowList();
				workFlowList.setWorkflowid(workFlowId);
				for (int index = 0; index < approveManIds.size(); index++) {
					// 每个审批人的标识

					workFlowList.setManname(apprvoveManNames.get(index));
					workFlowList.setManid(approveManIds.get(index));
					workFlowList.setOrderid(index + 1);
					workFlowListMapper.addWorkFlowList(workFlowList);
				}

			}
		}else{
			
			//以下的情况是改后的人数等于改前的人数时的逻辑
			// 再更新流程链表的记录，根据每条记录的主键去修改每条记录抑或是根据审批人员的标识去修改此条记录
			// 先通过流程编号找到该流程下的节点记录的编号
			List<Integer> workFlowListTabIds = new ArrayList<Integer>();// 流程链记录编号组成的集合
			for (WorkFlowList workFlowList : workFlowLists) {
				workFlowListTabIds.add(workFlowList.getTabid());
				
			}
			WorkFlowList workFlowList = new WorkFlowList();
			for (int index = 0; index < approveManIds.size(); index++) {
				// 每个审批人的标识
				// 这是新值
				workFlowList.setTabid(workFlowListTabIds.get(index));
				workFlowList.setManname(apprvoveManNames.get(index));
				workFlowList.setManid(approveManIds.get(index));
				workFlowList.setOrderid(index + 1);
				workFlowListMapper.setWorkFlowListByPrimaryKey(workFlowList);
			}
		}

		// TODO Auto-generated method stub
		return 1;
	}

	/**
	 * 删除流程
	 * 
	 */
	@Override
	public int deleteWorkFlow(int workFlowId) throws Exception {
		// TODO Auto-generated method stub
		// 先删除流程表记录再删除该流程下的所有流程链记录
		workFlowMapper.deleteByPrimaryKey(workFlowId);
		workFlowListMapper.deleteByWorkFlowId(workFlowId);
		return 1;
	}

	/**
	 * 通过条件获取流程表记录总数
	 * 
	 * @param schoolId
	 * @param flowName
	 * @return
	 */
	@Override
	public int getCountByCondition(String schoolId, String flowName) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schoolId", schoolId);
		params.put("flowNameKey", flowName);
		return workFlowMapper.getCountByCondition(params);
	}

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
	@Override
	public List<WorkFlow> getWorkFlowDataByPagination(String schoolId, String flowName, int pageIndex, int pageSize)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("schoolId", schoolId);
		parameters.put("flowName", flowName);
		parameters.put("pageIndex", pageIndex);
		parameters.put("pageSize", pageSize);
//		System.out.println("parameters====" + parameters);
		return workFlowMapper.getWorkFlowDataByPagination(parameters);
	}

	/**
	 * 通过流程编号获取其下的多个节点
	 * 
	 * @param workFlowId
	 * @return
	 */
	@Override
	public List<WorkFlowList> getWorkFlowListByWorkFlowId(int workFlowId) throws Exception {
		// TODO Auto-generated method stub
		return workFlowListMapper.getWorkFlowListByWorkFlowId(workFlowId);
	}

	/**
	 * 
	 * 获取选择用流程列表
	 * 
	 * @param schoolId
	 * @return
	 */
	@Override
	public List<WorkFlow> getSelWorkFlowByCondition(String schoolId) throws Exception {
		// TODO Auto-generated method stub

		return workFlowMapper.getSelWorkFlowByCondition(schoolId);
	}

	/**
	 * 通过学校标识和流程名称查询记录条数
	 * 
	 * @param schoolId
	 * @param flowName
	 * @return
	 */
	@Override
	public int getWorkFlowCountByCondition(String schoolId, String flowName) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schoolId", schoolId);
		params.put("flowNameKey", flowName);
		return workFlowMapper.getWorkFlowCountByCondition(params);
	}

	/**
	 * 通过流程编号查找对应记录的学校编号
	 * 
	 * @param recordId
	 * @return
	 */
	@Override
	public WorkFlow getSchoolIdByRecordId(int recordId) throws Exception {
		// TODO Auto-generated method stub
		return workFlowMapper.getSchoolIdByRecordId(recordId);
	}
	/**
	 * 通过学校标识和流程名称查找记录标识
	 * @param schoolId
	 * @param flowName
	 * @return
	 * @throws Exception
	 */
	@Override
	public WorkFlow getTableRecordByCondition(String schoolId, String flowName) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schoolId", schoolId);
		params.put("flowName", flowName);
		return workFlowMapper.getTableRecordByCondition(params);
		
	}
}
