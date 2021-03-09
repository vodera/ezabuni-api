package com.turubini.ezabuni.repositories;

import com.turubini.ezabuni.domain.TenderRequest;
import com.turubini.ezabuni.exceptions.EzBadRequestException;
import com.turubini.ezabuni.exceptions.EzResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;


@Repository
public class TenderRequestRepositoryImpl implements TenderRequestRepository {

    private static final String SQL_CREATE = "INSERT INTO TENDER_REQUEST(TENDER_REQUESTID, DEPARTMENTID, TENDER_REQUESTNAME, " +
            "TENDER_REQUESTBRIEF, TENDER_REQUESTDOCUMENT, ENTITY_NAME, PROCESSING_STAGE, APPROVAL_STATUS, USERID) VALUES " +
            "(NEXTVAL('TENDER_REQUEST_SEQ'), ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_BY_ID = "SELECT TENDER_REQUESTID, DEPARTMENTID, TENDER_REQUESTNAME, " +
            "TENDER_REQUESTBRIEF, TENDER_REQUESTDOCUMENT, ENTITY_NAME, PROCESSING_STAGE, APPROVAL_STATUS, USERID FROM TENDER_REQUEST WHERE USERID = ? AND TENDER_REQUESTID = ? GROUP BY TENDER_REQUESTID";

    private static final String SQL_FIND_ALL = "SELECT TENDER_REQUESTID, DEPARTMENTID, TENDER_REQUESTNAME, " +
            "TENDER_REQUESTBRIEF, TENDER_REQUESTDOCUMENT, ENTITY_NAME, PROCESSING_STAGE, APPROVAL_STATUS, USERID FROM TENDER_REQUEST WHERE USERID = ? GROUP BY TENDER_REQUESTID";

    private static final String SQL_UPDATE = "UPDATE TENDER_REQUEST SET DEPARTMENTID = ?, TENDER_REQUESTNAME = ?, " +
            "TENDER_REQUESTBRIEF = ?, TENDER_REQUESTDOCUMENT = ?, ENTITY_NAME = ?, PROCESSING_STAGE = ?, APPROVAL_STATUS = ? WHERE USERID = ? AND TENDER_REQUESTID = ?";

    private static final String SQL_DELETE = "DELETE FROM TENDER_REQUEST WHERE USERID = ? AND TENDER_REQUESTID = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<TenderRequest> findAll(Integer userId) throws EzResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userId}, tenderRequestRowMapper);
    }

    @Override
    public TenderRequest findById(Integer userId, Integer tenderRequestId) throws EzResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, tenderRequestId}, tenderRequestRowMapper);
        }catch (Exception e) {
            throw new EzResourceNotFoundException ("Tender request not found.");
        }
    }

    @Override
    public Integer create(Integer departmentId, String tenderRequestName, String tenderRequestBrief, String tenderRequestDocument, String entityName, String processingStage, String approvalStatus, Integer userId) throws EzBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, departmentId);
                ps.setString(2, tenderRequestName);
                ps.setString(3, tenderRequestBrief);
                ps.setString(4, tenderRequestDocument);
                ps.setString(5, entityName);
                ps.setString(6, processingStage);
                ps.setString(7, approvalStatus);
                ps.setInt(8, userId);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("TENDER_REQUESTID");
        }catch (Exception e) {
            throw new EzBadRequestException("Invalid request. Failed to create tender request");
        }
    }

    @Override
    public void update(Integer userId, Integer tenderRequestId, TenderRequest tenderRequest) throws EzBadRequestException {
    try {
        jdbcTemplate.update(SQL_UPDATE, new Object[]{tenderRequest.getDepartmentId(), tenderRequest.getTenderRequestName(), tenderRequest.getTenderRequestBrief(), tenderRequest.getTenderRequestDocument(), tenderRequest.getApprovalStatus(), tenderRequest.getEntityName(), tenderRequest.getProcessingStage(), userId,
        tenderRequestId});
    }catch (Exception e){
        throw new EzBadRequestException("Invalid request");
    }
    }

    @Override
    public void removeById(Integer userId, Integer tenderRequestId) throws EzResourceNotFoundException {
        int count = jdbcTemplate.update(SQL_DELETE, new Object[]{userId, tenderRequestId});
        if(count == 0)
            throw new EzResourceNotFoundException("Tender Request not found");
    }

    private RowMapper<TenderRequest> tenderRequestRowMapper = ((rs, rowNum) ->{
        return new TenderRequest(
                rs.getInt("TENDER_REQUESTID"),
                rs.getInt("DEPARTMENTID"),
                rs.getString("TENDER_REQUESTNAME"),
                rs.getString("TENDER_REQUESTBRIEF"),
                rs.getString("TENDER_REQUESTDOCUMENT"),
                rs.getString("ENTITY_NAME"),
                rs.getString("PROCESSING_STAGE"),
                rs.getString("APPROVAL_STATUS"),
                rs.getInt("USERID"));
    });
}
