package com.goldeneyes.IDao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.goldeneyes.pojo.WorkFlowList;

@Mapper
public interface WorkFlowListMapper {
    int deleteByPrimaryKey(Integer tabid);

    int insert(WorkFlowList record);

    int insertSelective(WorkFlowList record);

    WorkFlowList selectByPrimaryKey(Integer tabid);

    int updateByPrimaryKeySelective(WorkFlowList record);

    int updateByPrimaryKey(WorkFlowList record);
    /**
	 * 添加流程链
	 * 
	 * @param record
	 * @return
	 */
	int addWorkFlowList(WorkFlowList record);

	/**
	 * 更新流程链
	 * 
	 * @param record
	 * @return
	 */
	int setWorkFlowListByPrimaryKey(WorkFlowList record);

	/**
	 * 删除流程链记录
	 * 
	 * @param tabid
	 * @return
	 */
	int deleteByWorkFlowId(int workFlowId);

	/**
	 * 通过流程编号获取其下的多个节点
	 * 
	 * @param workFlowId
	 * @return
	 */
	List<WorkFlowList> getWorkFlowListByWorkFlowId(int workFlowId);
}