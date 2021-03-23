package com.turubini.ezabuni.resources;

import com.turubini.ezabuni.domain.TenderRequest;
import com.turubini.ezabuni.services.TenderRequestService.TenderRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tender_request")
public class TenderRequestResource {

    @Autowired
    TenderRequestService tenderRequestService;

    @GetMapping("")
    public ResponseEntity<List<TenderRequest>> getAllTenderRequests(HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("userId");
        List<TenderRequest> tenderRequests = tenderRequestService.fetchAllTenderRequestsByUserId(userId);
        return new ResponseEntity<>(tenderRequests, HttpStatus.OK);
    }

    @GetMapping("/{tenderRequestId}")
    public ResponseEntity<TenderRequest> getTenderRequestById(HttpServletRequest request,
                                                              @PathVariable("tenderRequestId") Integer tenderRequestId) {
        int userId = (Integer) request.getAttribute("userId");
        TenderRequest tenderRequest = tenderRequestService.fetchTenderRequestsById(userId, tenderRequestId);
        return new ResponseEntity<>(tenderRequest, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<TenderRequest> addTenderRequest(HttpServletRequest request,
                                                          @RequestBody Map<String, Object> tenderRequestMap) {

        int departmentId = (Integer) tenderRequestMap.get("departmentId");
        String tenderRequestName = (String) tenderRequestMap.get("tenderRequestName");
        String tenderRequestBrief = (String) tenderRequestMap.get("tenderRequestBrief");
        String tenderRequestDocument = (String) tenderRequestMap.get("tenderRequestDocument");
        String entityName = (String) tenderRequestMap.get("entityName");
        String processingStage = (String) tenderRequestMap.get("processingStage");
        String approvalStatus = (String) tenderRequestMap.get("approvalStatus");
        int userId = (Integer) request.getAttribute("userId");
        TenderRequest tenderRequest = tenderRequestService.addTenderRequest(departmentId, tenderRequestName,
                tenderRequestBrief, tenderRequestDocument, entityName, processingStage, approvalStatus, userId);

        return  new ResponseEntity<>(tenderRequest, HttpStatus.CREATED);
    }

    @PutMapping("/{tenderRequestId}")
    public ResponseEntity<Map<String, Boolean>> updateTenderRequest(HttpServletRequest request,
                                                               @PathVariable("tenderRequestId") Integer tenderRequestId,
                                                               @RequestBody TenderRequest tenderRequest) {
        int userId = (Integer) request.getAttribute("userId");
        tenderRequestService.updateTenderRequest(userId, tenderRequestId, tenderRequest);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{tenderRequestId}")
    public ResponseEntity<Map<String, Boolean>> deleteTenderRequest(HttpServletRequest request,
                                                               @PathVariable("tenderRequestId") Integer tenderRequestId) {
        int userId = (Integer) request.getAttribute("userId");
        tenderRequestService.removeTenderRequest(userId, tenderRequestId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }



}
