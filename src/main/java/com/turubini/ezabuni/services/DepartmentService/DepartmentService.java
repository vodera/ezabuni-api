package com.turubini.ezabuni.services.DepartmentService;

import com.turubini.ezabuni.domain.Department;
import com.turubini.ezabuni.domain.TenderRequest;
import com.turubini.ezabuni.exceptions.EzBadRequestException;
import com.turubini.ezabuni.exceptions.EzResourceNotFoundException;

import java.util.List;

public interface DepartmentService {

    //fetch all tender requests by passing user as the parameter
    List<Department> fetchAllDepartmentsByUserId (Integer userId);

    //fetch single department by passing userid and departmentid as parameters
    Department fetchDepartmentById (Integer userId, Integer departmentId) throws EzResourceNotFoundException;

    //creating a department
    Department addDepartment (String departmentName, String departmentDescription, Integer userId) throws EzBadRequestException;

    //updating a department
    void updateDepartment(Integer userId, Integer departmentId, Department department ) throws EzBadRequestException;

    //remove a department
    void removeDepartment(Integer userId, Integer departmentId) throws EzResourceNotFoundException;
}
