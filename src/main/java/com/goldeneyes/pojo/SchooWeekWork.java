package com.goldeneyes.pojo;

import java.util.Date;

public class SchooWeekWork {
    private Integer tabid;

    private Integer periodid;

    private Integer week;

    private Date begintime;

    private Date endtime;

    private String weekwork;

    private String workdept;

    private Byte status;

    public Integer getTabid() {
        return tabid;
    }

    public void setTabid(Integer tabid) {
        this.tabid = tabid;
    }

    public Integer getPeriodid() {
        return periodid;
    }

    public void setPeriodid(Integer periodid) {
        this.periodid = periodid;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getWeekwork() {
        return weekwork;
    }

    public void setWeekwork(String weekwork) {
        this.weekwork = weekwork == null ? null : weekwork.trim();
    }

    public String getWorkdept() {
        return workdept;
    }

    public void setWorkdept(String workdept) {
        this.workdept = workdept == null ? null : workdept.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}