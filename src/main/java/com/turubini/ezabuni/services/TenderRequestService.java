package com.turubini.ezabuni.services;

import com.turubini.ezabuni.domain.TenderRequest;
import com.turubini.ezabuni.exceptions.EzBadRequestException;
import com.turubini.ezabuni.exceptions.EzResourceNotFoundException;

import java.util.List;

public interface TenderRequestService {

    //fetch all tender requests by passing departmentId as the parameter
    //List<TenderRequest> fetchAllTenderRequestsByDepartmentId (Integer departmentId);

    //fetch all tender requests by passing user as the parameter
    List<TenderRequest> fetchAllTenderRequestsByUserId (Integer userId);

    //fetch single tender request by passing userid and tenderrequestid as parameters
    TenderRequest fetchTenderRequestsById (Integer userId, Integer tenderRequestId) throws EzResourceNotFoundException;

    //creating a tender request
    TenderRequest addTenderRequest ( Integer departmentId, String tenderRequestName, String tenderRequestBrief,
                                        String tenderRequestDocument, String entityName, String processingStage, String approvalStatus,
                                        Integer userId) throws EzBadRequestException;

    //updating a Tender Request
    void updateTenderRequest(Integer userId, Integer tenderRequestId, TenderRequest tenderRequest ) throws EzBadRequestException;

    //remove a Tender Request with all transactions
    void removeTenderRequest(Integer userId, Integer tenderRequestId) throws EzResourceNotFoundException;
}
