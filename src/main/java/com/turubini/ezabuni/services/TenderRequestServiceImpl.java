package com.turubini.ezabuni.services;

import com.turubini.ezabuni.domain.TenderRequest;
import com.turubini.ezabuni.exceptions.EzBadRequestException;
import com.turubini.ezabuni.exceptions.EzResourceNotFoundException;
import com.turubini.ezabuni.repositories.TenderRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TenderRequestServiceImpl implements TenderRequestService {

    @Autowired
    TenderRequestRepository tenderRequestRepository;

    @Override
    public List<TenderRequest> fetchAllTenderRequestsByUserId(Integer userId) {
        return tenderRequestRepository.findAll(userId);
    }

    @Override
    public TenderRequest fetchTenderRequestsById(Integer userId, Integer tenderRequestId) throws EzResourceNotFoundException {
        return tenderRequestRepository.findById(userId, tenderRequestId);
    }

    @Override
    public TenderRequest addTenderRequest(Integer departmentId, String tenderRequestName, String tenderRequestBrief, String tenderRequestDocument, String entityName, String processingStage, String approvalStatus, Integer userId) throws EzBadRequestException {
        Integer tenderRequestId = tenderRequestRepository.create(departmentId, tenderRequestName, tenderRequestBrief, tenderRequestDocument, entityName, processingStage, approvalStatus, userId);
        return tenderRequestRepository.findById(userId, tenderRequestId);
    }

    @Override
    public void updateTenderRequest(Integer userId, Integer tenderRequestId, TenderRequest tenderRequest) throws EzBadRequestException {
    tenderRequestRepository.update(userId, tenderRequestId, tenderRequest);
    }

    @Override
    public void removeTenderRequest(Integer userId, Integer tenderRequestId) throws EzResourceNotFoundException {
        tenderRequestRepository.removeById(userId, tenderRequestId);
    }
}
