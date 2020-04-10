/*----------------------------------------------------------------
 *  Copyright (C) 2017山东金视野教育科技股份有限公司
 * 版权所有。 
 *
 * 文件名：ApplyAndApproveVO
 * 文件功能描述：申请和审批VO
 *
 * 
 * 创建标识：konglm20180129
 *
 * 修改标识：
 * 修改描述：
 *----------------------------------------------------------------*/

package com.goldeneyes.vo;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class ApplyAndApproveVO {
	private Integer applyid;
	
	private Integer approveid;

    private String schoolid;

    private String applytitle;

    private String applycontent;

    private String applyencname;

    private String applyencaddr;

    private String applymanid;

    private String applymanname;
    
    private String applymanpic;

    private Date sendtime;
    
    private Byte progress;

    private Integer upperid;

    private String approvemanid;

    private String approvemanname;
    
    private String approvemanpic;

    private String approvecontent;

    private Date approvetime;

    private Byte status;

	public Integer getApplyid() {
		return applyid;
	}

	public void setApplyid(Integer applyid) {
		this.applyid = applyid;
	}

	public Integer getApproveid() {
		return approveid;
	}

	public void setApproveid(Integer approveid) {
		this.approveid = approveid;
	}

	public String getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
	}

	public String getApplytitle() {
		return applytitle;
	}

	public void setApplytitle(String applytitle) {
		this.applytitle = applytitle;
	}

	public String getApplycontent() {
		return applycontent;
	}

	public void setApplycontent(String applycontent) {
		this.applycontent = applycontent;
	}

	public String getApplyencname() {
		return applyencname;
	}

	public void setApplyencname(String applyencname) {
		this.applyencname = applyencname;
	}

	public String getApplyencaddr() {
		return applyencaddr;
	}

	public void setApplyencaddr(String applyencaddr) {
		this.applyencaddr = applyencaddr;
	}

	public String getApplymanid() {
		return applymanid;
	}

	public void setApplymanid(String applymanid) {
		this.applymanid = applymanid;
	}

	public String getApplymanname() {
		return applymanname;
	}

	public void setApplymanname(String applymanname) {
		this.applymanname = applymanname;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public Byte getProgress() {
		return progress;
	}

	public void setProgress(Byte progress) {
		this.progress = progress;
	}

	public Integer getUpperid() {
		return upperid;
	}

	public void setUpperid(Integer upperid) {
		this.upperid = upperid;
	}

	public String getApprovemanid() {
		return approvemanid;
	}

	public void setApprovemanid(String approvemanid) {
		this.approvemanid = approvemanid;
	}

	public String getApprovemanname() {
		return approvemanname;
	}

	public void setApprovemanname(String approvemanname) {
		this.approvemanname = approvemanname;
	}

	public String getApprovecontent() {
		return approvecontent;
	}

	public void setApprovecontent(String approvecontent) {
		this.approvecontent = approvecontent;
	}

	public Date getApprovetime() {
		return approvetime;
	}

	public void setApprovetime(Date approvetime) {
		this.approvetime = approvetime;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
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

	/**
	 * @return the approvemanpic
	 */
	public String getApprovemanpic() {
		return approvemanpic;
	}

	/**
	 * @param approvemanpic the approvemanpic to set
	 */
	public void setApprovemanpic(String approvemanpic) {
		this.approvemanpic = approvemanpic;
	}
    
    

}
