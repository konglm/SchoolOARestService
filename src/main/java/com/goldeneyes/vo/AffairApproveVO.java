/*----------------------------------------------------------------
 *  Copyright (C) 2017山东金视野教育科技股份有限公司
 * 版权所有。 
 *
 * 文件名：AffairApproveVO
 * 文件功能描述：审批VO
 *
 * 
 * 创建标识：konglm20180127
 *
 * 修改标识：
 * 修改描述：
 *----------------------------------------------------------------*/
package com.goldeneyes.vo;

import java.util.Date;

public class AffairApproveVO {
    private Integer tabid;

    private Integer applyid;
    
    private String applytitle;

    private String applymanname;
    
    private String applymanpic;

    private String progress;

    private String status;

    private Date sendtime;

    private Date approvetime;


	public Integer getTabid() {
		return tabid;
	}

	public void setTabid(Integer tabid) {
		this.tabid = tabid;
	}

	public Integer getApplyid() {
        return applyid;
    }

    public void setApplyid(Integer applyid) {
        this.applyid = applyid;
    }

	public String getApplytitle() {
		return applytitle;
	}

	public void setApplytitle(String applytitle) {
		this.applytitle = applytitle;
	}

	public String getApplymanname() {
		return applymanname;
	}

	public void setApplymanname(String applymanname) {
		this.applymanname = applymanname;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public Date getApprovetime() {
		return approvetime;
	}

	public void setApprovetime(Date approvetime) {
		this.approvetime = approvetime;
	}

	/**
	 * @return the applymanpic
	 */
	public String getApplymanpic() {
		return applymanpic;
	}

	/**
	 * @param applymanpic the applymanpic to set
	 */
	public void setApplymanpic(String applymanpic) {
		this.applymanpic = applymanpic;
	}

    
}