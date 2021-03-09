package com.turubini.ezabuni.repositories;

import com.turubini.ezabuni.domain.TenderRequest;
import com.turubini.ezabuni.exceptions.EzBadRequestException;
import com.turubini.ezabuni.exceptions.EzResourceNotFoundException;
import jdk.jfr.Category;

import java.util.List;

public interface TenderRequestRepository {

//    List<TenderRequest> findAllByDepartmentId(Integer userId, Integer departmentId);

    List<TenderRequest> findAll(Integer userId) throws EzResourceNotFoundException;

    TenderRequest findById(Integer userId, Integer tenderRequestId) throws EzResourceNotFoundException;

    Integer create (Integer departmentId, String tenderRequestName, String tenderRequestBrief,
                    String tenderRequestDocument, String entityName, String processingStage, String approvalStatus,
                    Integer userId) throws EzBadRequestException;

    void update (Integer userId, Integer tenderRequestId, TenderRequest tenderRequest) throws EzBadRequestException;

    void removeById (Integer userId, Integer tenderRequestId) throws EzResourceNotFoundException;
}
