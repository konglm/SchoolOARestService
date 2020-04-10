package com.goldeneyes.pojo;

import java.util.Date;

public class CooperateNotice {
    private Integer tabid;

    private String schoolid;

    private String noticetitle;

    private String noticecontent;

    private String noticeencname;

    private String noticeencaddr;

    private Byte smssync;

    private Long sendmanid;

    private String sendmancode;

    private String sendmanname;

    private String sendmanpic;

    private Date sendtime;

    private Byte progress;

    private Byte status;

    public Integer getTabid() {
        return tabid;
    }

    public void setTabid(Integer tabid) {
        this.tabid = tabid;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid == null ? null : schoolid.trim();
    }

    public String getNoticetitle() {
        return noticetitle;
    }

    public void setNoticetitle(String noticetitle) {
        this.noticetitle = noticetitle == null ? null : noticetitle.trim();
    }

    public String getNoticecontent() {
        return noticecontent;
    }

    public void setNoticecontent(String noticecontent) {
        this.noticecontent = noticecontent == null ? null : noticecontent.trim();
    }

    public String getNoticeencname() {
        return noticeencname;
    }

    public void setNoticeencname(String noticeencname) {
        this.noticeencname = noticeencname == null ? null : noticeencname.trim();
    }

    public String getNoticeencaddr() {
        return noticeencaddr;
    }

    public void setNoticeencaddr(String noticeencaddr) {
        this.noticeencaddr = noticeencaddr == null ? null : noticeencaddr.trim();
    }

    public Byte getSmssync() {
        return smssync;
    }

    public void setSmssync(Byte smssync) {
        this.smssync = smssync;
    }

    public Long getSendmanid() {
        return sendmanid;
    }

    public void setSendmanid(Long sendmanid) {
        this.sendmanid = sendmanid;
    }

    public String getSendmancode() {
        return sendmancode;
    }

    public void setSendmancode(String sendmancode) {
        this.sendmancode = sendmancode == null ? null : sendmancode.trim();
    }

    public String getSendmanname() {
        return sendmanname;
    }

    public void setSendmanname(String sendmanname) {
        this.sendmanname = sendmanname == null ? null : sendmanname.trim();
    }

    public String getSendmanpic() {
        return sendmanpic;
    }

    public void setSendmanpic(String sendmanpic) {
        this.sendmanpic = sendmanpic == null ? null : sendmanpic.trim();
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}