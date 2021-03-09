package com.turubini.ezabuni.domain;

public class TenderRequest {

    private Integer tenderRequestId;
    private Integer departmentId;
    private String tenderRequestName;
    private String tenderRequestBrief;
    private String tenderRequestDocument;
    private String entityName;
    private String processingStage;
    private String approvalStatus;
    private Integer userid;

    public TenderRequest(Integer tenderRequestId, Integer departmentId, String tenderRequestName, String tenderRequestBrief, String tenderRequestDocument, String entityName, String processingStage, String approvalStatus, Integer userid) {
        this.tenderRequestId = tenderRequestId;
        this.departmentId = departmentId;
        this.tenderRequestName = tenderRequestName;
        this.tenderRequestBrief = tenderRequestBrief;
        this.tenderRequestDocument = tenderRequestDocument;
        this.entityName = entityName;
        this.processingStage = processingStage;
        this.approvalStatus = approvalStatus;
        this.userid = userid;
    }

    public Integer getTenderRequestId() {
        return tenderRequestId;
    }

    public void setTenderRequestId(Integer tenderRequestId) {
        this.tenderRequestId = tenderRequestId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getTenderRequestName() {
        return tenderRequestName;
    }

    public void setTenderRequestName(String tenderRequestName) {
        this.tenderRequestName = tenderRequestName;
    }

    public String getTenderRequestBrief() {
        return tenderRequestBrief;
    }

    public void setTenderRequestBrief(String tenderRequestBrief) {
        this.tenderRequestBrief = tenderRequestBrief;
    }

    public String getTenderRequestDocument() {
        return tenderRequestDocument;
    }

    public void setTenderRequestDocument(String tenderRequestDocument) {
        this.tenderRequestDocument = tenderRequestDocument;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getProcessingStage() {
        return processingStage;
    }

    public void setProcessingStage(String processingStage) {
        this.processingStage = processingStage;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
