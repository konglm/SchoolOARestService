package com.goldeneyes.IDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.goldeneyes.pojo.AffairApply;

@Mapper
public interface AffairApplyMapper {
    int deleteByPrimaryKey(Integer tabid);

    int insert(AffairApply record);

    int insertSelective(AffairApply record);

    AffairApply selectByPrimaryKey(Integer tabid);

    int updateByPrimaryKeySelective(AffairApply record);

    int updateByPrimaryKey(AffairApply record);
    
    /**
	 * 新增申请
	 * 
	 * @param record
	 * @return
	 */
	int addAffairApply(AffairApply record);

	/**
	 * 撤销申请
	 * 
	 * @param applyId
	 * @return
	 */
	int setAffairApplyUndo(int applyId);

	/**
	 * 更新申请状态
	 * 
	 * @param approveId
	 * @param status
	 * @return
	 */
	int setApplyStatus(int approveId, int status);

	/**
	 * 获取申请数量
	 * 
	 * @param schoolId
	 * @param title
	 * @param applyManId
	 * @param status
	 * @param progress
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	int getAffairApplyCnt(Map<String, Object> params);

	/**
	 * 获取申请列表
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
	 */
	List<AffairApply> getAffairApply(Map<String, Object> params);
	/**
	 * 删除事务及文件申请
	 * @param params
	 * @return
	 */
	int delAffairApplys(Map<String, Object> params);
	/**
	 * 获取全部申请数量
	 * @param params
	 * @return
	 */
	int getAllAffairApplyCnt(Map<String, Object> params);
	/**
	 * 获取全部申请列表
	 * @param params
	 * @return
	 */
	List<AffairApply> getAllAffairApply(Map<String, Object> params);
	/**
	 * 真实删除申请和审批
	 * @param params
	 * @return
	 */
	int realDelAffairApplys(Map<String, Object> params);
}