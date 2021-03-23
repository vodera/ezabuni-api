package com.turubini.ezabuni.repositories.DepartmentRepository;

import com.turubini.ezabuni.domain.Department;
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
public class DepartmentRepositoryImpl implements DepartmentRepository {

    public static final String SQL_CREATE="INSERT INTO DEPARTMENTS(DEPARTMENTID, DEPARTMENT_NAME, DEPARTMENT_DESCRIPTION, USERID) VALUES (NEXTVAL('DEPARTMENTS_SEQ'), ?, ?, ?)";
    public static final String SQL_FIND_BY_ID="SELECT DEPARTMENTID, DEPARTMENT_NAME, DEPARTMENT_DESCRIPTION, USERID FROM DEPARTMENTS WHERE USERID = ? AND DEPARTMENTID = ? GROUP BY DEPARTMENTID";
    public static final String SQL_FIND_ALL="SELECT DEPARTMENTID, DEPARTMENT_NAME, DEPARTMENT_DESCRIPTION, USERID FROM DEPARTMENTS WHERE USERID = ? GROUP BY DEPARTMENTID";
    public static final String SQL_UPDATE="UPDATE DEPARTMENTS SET DEPARTMENT_NAME = ?, DEPARTMENT_DESCRIPTION = ? WHERE USERID = ? AND DEPARTMENTID = ?";
    public static final String SQL_DELETE="DELETE FROM DEPARTMENTS WHERE USERID = ? AND DEPARTMENTID = ?";


    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public List<Department> findAll(Integer userId) throws EzResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userId}, departmentRowMapper);
    }

    @Override
    public Department findById(Integer userId, Integer departmentId) throws EzResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, departmentId}, departmentRowMapper);
        }catch (Exception e) {
            throw new EzResourceNotFoundException ("Department not found.");
        }
    }

    @Override
    public Integer create(String departmentName, String departmentDescription, Integer userId) throws EzBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, departmentName);
                ps.setString(2, departmentDescription);
                ps.setInt(3, userId);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("DEPARTMENTID");
        }catch (Exception e) {
            throw new EzBadRequestException("Invalid request. Failed to create department");
        }
    }

    @Override
    public void update(Integer userId, Integer departmentId, Department department) throws EzBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{department.getDepartmentName(), department.getDepartmentDescription(), userId,
                    departmentId});
        }catch (Exception e){
            throw new EzBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer userId, Integer departmentId) throws EzResourceNotFoundException {
        int count = jdbcTemplate.update(SQL_DELETE, new Object[]{userId, departmentId});
        if(count == 0)
            throw new EzResourceNotFoundException("Department not found");
    }

    private RowMapper<Department> departmentRowMapper = ((rs, rowNum) ->{
        return new Department(
                rs.getInt("DEPARTMENTID"),
                rs.getString("DEPARTMENT_NAME"),
                rs.getString("DEPARTMENT_DESCRIPTION"),
                rs.getInt("USERID"));
    });
}
