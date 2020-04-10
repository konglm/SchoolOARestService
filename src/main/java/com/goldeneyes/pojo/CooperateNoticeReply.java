package com.goldeneyes.pojo;

import java.util.Date;

public class CooperateNoticeReply {
    private Integer tabid;

    private Integer noticemanid;

    private String replycontent;

    private Date replytime;

    private String encname;

    private String encaddr;

    public Integer getTabid() {
        return tabid;
    }

    public void setTabid(Integer tabid) {
        this.tabid = tabid;
    }

    public Integer getNoticemanid() {
        return noticemanid;
    }

    public void setNoticemanid(Integer noticemanid) {
        this.noticemanid = noticemanid;
    }

    public String getReplycontent() {
        return replycontent;
    }

    public void setReplycontent(String replycontent) {
        this.replycontent = replycontent == null ? null : replycontent.trim();
    }

    public Date getReplytime() {
        return replytime;
    }

    public void setReplytime(Date replytime) {
        this.replytime = replytime;
    }

    public String getEncname() {
        return encname;
    }

    public void setEncname(String encname) {
        this.encname = encname == null ? null : encname.trim();
    }

    public String getEncaddr() {
        return encaddr;
    }

    public void setEncaddr(String encaddr) {
        this.encaddr = encaddr == null ? null : encaddr.trim();
    }
}