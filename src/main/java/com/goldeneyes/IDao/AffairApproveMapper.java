package com.goldeneyes.IDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.goldeneyes.pojo.AffairApprove;
import com.goldeneyes.vo.ApplyAndApproveVO;

@Mapper
public interface AffairApproveMapper {
    int deleteByPrimaryKey(Integer tabid);

    int insert(AffairApprove record);

    int insertSelective(AffairApprove record);

    AffairApprove selectByPrimaryKey(Integer tabid);

    int updateByPrimaryKeySelective(AffairApprove record);

    int updateByPrimaryKey(AffairApprove record);
    /**
     * 新增审批
     * @param record
     * @return
     */
    int addAffairApprove(AffairApprove record);
    /**
     * 判断申请是否已被审批
     * @param applyId
     * @return
     */
    int getApplyApproveCntById(int applyId);
    /**
     * 审批事件
     * @param record
     * @return
     */
    int setAffairApprove(AffairApprove record);
    /**
     * 是否所有审批都已经结束
     * @param approveId
     * @return
     */
    int isApplyFinished(int approveId);
    /**
     * 修改下级审批状态
     * @param approveId
     * @param status
     * @return
     */
    int setNextIdStatus(int approveId, int status);
    /**
     * 删除所有下级审批
     * @param approveId
     * @return
     */
    int delDownId(int approveId);
    /**
     * 获取审批数量
     * @param params
     * @return
     */
    int getAffairApproveCnt(Map<String, Object> params);
    /**
     * 获取审批列表
     * @param params
     * @return
     */
    List<ApplyAndApproveVO> getAffairApprove(Map<String, Object> params);
    /**
     * 通过申请获取审批列表
     * @param applyId
     * @return
     */
    List<AffairApprove> getAffairApproveByApply(int applyId);
    /**
     * 通过审批ID获取申请和审批数据
     * @param approveId
     * @return
     */
    ApplyAndApproveVO getAffairApproveById(int approveId);
    /**
     * 删掉申请下的审批
     * @param applyId
     * @return
     */
    int delApproveByApply(int applyId);
    /**
     * 获取未审批数量
     * @param schoolId
     * @param approveManId
     * @return
     */
    int getNoApproveCntByMan(String schoolId, Long approveManId);
    /**
     * 删除掉申请下的所有审批
     * @param params
     * @return
     */
    int realDelAffairApproves(Map<String, Object> params);
}