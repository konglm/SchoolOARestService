package com.goldeneyes.pojo;

import java.util.Date;

public class CooperateNoticeMan {
    private Integer tabid;

    private Integer noticeid;

    private String noticetag;

    private Long receivemanid;

    private String receivemancode;

    private String receivemanname;

    private String receivemanpic;

    private Date readtime;

    private Byte status;

    public Integer getTabid() {
        return tabid;
    }

    public void setTabid(Integer tabid) {
        this.tabid = tabid;
    }

    public Integer getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(Integer noticeid) {
        this.noticeid = noticeid;
    }

    public String getNoticetag() {
        return noticetag;
    }

    public void setNoticetag(String noticetag) {
        this.noticetag = noticetag == null ? null : noticetag.trim();
    }

    public Long getReceivemanid() {
        return receivemanid;
    }

    public void setReceivemanid(Long receivemanid) {
        this.receivemanid = receivemanid;
    }

    public String getReceivemancode() {
        return receivemancode;
    }

    public void setReceivemancode(String receivemancode) {
        this.receivemancode = receivemancode == null ? null : receivemancode.trim();
    }

    public String getReceivemanname() {
        return receivemanname;
    }

    public void setReceivemanname(String receivemanname) {
        this.receivemanname = receivemanname == null ? null : receivemanname.trim();
    }

    public String getReceivemanpic() {
        return receivemanpic;
    }

    public void setReceivemanpic(String receivemanpic) {
        this.receivemanpic = receivemanpic == null ? null : receivemanpic.trim();
    }

    public Date getReadtime() {
        return readtime;
    }

    public void setReadtime(Date readtime) {
        this.readtime = readtime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}