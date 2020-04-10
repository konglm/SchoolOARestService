package com.goldeneyes.IDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.goldeneyes.pojo.WorkFlow;

@Mapper
public interface WorkFlowMapper {
    int deleteByPrimaryKey(Integer tabid);

    int insert(WorkFlow record);

    int insertSelective(WorkFlow record);

    WorkFlow selectByPrimaryKey(Integer tabid);

    int updateByPrimaryKeySelective(WorkFlow record);

    int updateByPrimaryKey(WorkFlow record);
    /**
	 * 添加流程
	 * 
	 * @param record
	 * @return
	 */
	int addWorkFlow(WorkFlow record);

	/**
	 * 修改流程
	 * 
	 * @return
	 */
	int setWorkFlowById(WorkFlow record);
	/**
	 * 通过条件获取流程表记录总数
	 * @param schoolId
	 * @param flowName
	 * @return
	 */
	int getCountByCondition(Map<String,Object> parameters);
	
	/**
	 * 分页或整页获取流程数据以列表形式
	 * @param parameters
	 * @return
	 */
	List<WorkFlow> getWorkFlowDataByPagination(Map<String,Object> parameters);
	
	/**
	 * 
	 * 获取选择用流程列表
	 * @param schoolId
	 * @return
	 */
	List<WorkFlow> getSelWorkFlowByCondition(String schoolId);
	/**
	 * 通过学校标识和流程名称查询记录条数
	 * @param schoolId
	 * @param flowName
	 * @return
	 */
	int getWorkFlowCountByCondition(Map<String,Object> parameters);
	/**
	 * 通过流程编号查找对应记录的学校编号
	 * @param recordId
	 * @return
	 */
	WorkFlow getSchoolIdByRecordId(int recordId);
	/**
	 * 通过学校标识和流程名称查找记录标识
	 * @param parameters
	 * @return
	 */
	WorkFlow getTableRecordByCondition(Map<String,Object> parameters);
}