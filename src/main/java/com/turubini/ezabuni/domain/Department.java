package com.turubini.ezabuni.domain;

public class Department {

    private Integer departmentid;
    private String departmentName;
    private String departmentDescription;
    private Integer userId;


    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Department(Integer departmentid, String departmentName, String departmentDescription, Integer userId) {
        this.departmentid = departmentid;
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
        this.userId = userId;
    }
}
