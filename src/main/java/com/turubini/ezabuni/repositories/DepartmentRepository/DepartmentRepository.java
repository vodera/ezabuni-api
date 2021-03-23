package com.turubini.ezabuni.repositories.DepartmentRepository;

import com.turubini.ezabuni.domain.Department;
import com.turubini.ezabuni.exceptions.EzBadRequestException;
import com.turubini.ezabuni.exceptions.EzResourceNotFoundException;

import java.util.List;

public interface DepartmentRepository {

    List<Department> findAll(Integer userId) throws EzResourceNotFoundException;

    Department findById(Integer userId, Integer tenderRequestId) throws EzResourceNotFoundException;

    Integer create (String departmentName, String departmentDescription, Integer userId) throws EzBadRequestException;

    void update (Integer userId, Integer departmentId, Department department) throws EzBadRequestException;

    void removeById (Integer userId, Integer departmentId) throws EzResourceNotFoundException;
}
