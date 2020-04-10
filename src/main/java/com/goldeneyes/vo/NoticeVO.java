/*----------------------------------------------------------------
 *  Copyright (C) 2017山东金视野教育科技股份有限公司
 * 版权所有。 
 *
 * 文件名：NoticeVO
 * 文件功能描述：通知VO
 *
 * 
 * 创建标识：konglm20180116
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
public class NoticeVO {
	
	private Integer noticeid;

    private String noticetitle;
    
    private String sendmanid;
    
    private String sendmanname;
    
    private String sendmanpic;

    private String noticestatus;

    private String noticeprogress;
    
    private Date sendtime;

	public Integer getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(Integer noticeid) {
		this.noticeid = noticeid;
	}

	public String getNoticetitle() {
		return noticetitle;
	}

	public void setNoticetitle(String noticetitle) {
		this.noticetitle = noticetitle;
	}

	public String getNoticestatus() {
		return noticestatus;
	}

	public void setNoticestatus(String noticestatus) {
		this.noticestatus = noticestatus;
	}

	public String getNoticeprogress() {
		return noticeprogress;
	}

	public void setNoticeprogress(String noticeprogress) {
		this.noticeprogress = noticeprogress;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public String getSendmanid() {
		return sendmanid;
	}

	public void setSendmanid(String sendmanid) {
		this.sendmanid = sendmanid;
	}

	public String getSendmanname() {
		return sendmanname;
	}

	public void setSendmanname(String sendmanname) {
		this.sendmanname = sendmanname;
	}

	/**
	 * @return the sendmanpic
	 */
	public String getSendmanpic() {
		return sendmanpic;
	}

	/**
	 * @param sendmanpic the sendmanpic to set
	 */
	public void setSendmanpic(String sendmanpic) {
		this.sendmanpic = sendmanpic;
	}
    

}
