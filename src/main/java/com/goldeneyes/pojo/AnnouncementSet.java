package com.goldeneyes.pojo;

public class AnnouncementSet {
    private Integer tabid;

    private String schoolid;

    private Long checkmanid;

    private String checkmancode;

    private String checkmanname;

    private String checkmanpic;

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

    public Long getCheckmanid() {
        return checkmanid;
    }

    public void setCheckmanid(Long checkmanid) {
        this.checkmanid = checkmanid;
    }

    public String getCheckmancode() {
        return checkmancode;
    }

    public void setCheckmancode(String checkmancode) {
        this.checkmancode = checkmancode == null ? null : checkmancode.trim();
    }

    public String getCheckmanname() {
        return checkmanname;
    }

    public void setCheckmanname(String checkmanname) {
        this.checkmanname = checkmanname == null ? null : checkmanname.trim();
    }

    public String getCheckmanpic() {
        return checkmanpic;
    }

    public void setCheckmanpic(String checkmanpic) {
        this.checkmanpic = checkmanpic == null ? null : checkmanpic.trim();
    }
}