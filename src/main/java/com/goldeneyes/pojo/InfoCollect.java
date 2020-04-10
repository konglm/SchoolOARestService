package com.goldeneyes.pojo;

import java.util.Date;

public class InfoCollect {
    private Integer tabid;

    private String schoolid;

    private String title;

    private String content;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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