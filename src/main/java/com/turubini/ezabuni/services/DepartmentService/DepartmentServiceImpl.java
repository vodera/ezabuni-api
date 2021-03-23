package com.turubini.ezabuni.services.DepartmentService;


import com.turubini.ezabuni.domain.Department;
import com.turubini.ezabuni.exceptions.EzBadRequestException;
import com.turubini.ezabuni.exceptions.EzResourceNotFoundException;
import com.turubini.ezabuni.repositories.DepartmentRepository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<Department> fetchAllDepartmentsByUserId(Integer userId) {
        return departmentRepository.findAll(userId);
    }

    @Override
    public Department fetchDepartmentById(Integer userId, Integer departmentId) throws EzResourceNotFoundException {
        return departmentRepository.findById(userId, departmentId);
    }

    @Override
    public Department addDepartment(String departmentName, String departmentDescription, Integer userId) throws EzBadRequestException {
        Integer departmentId = departmentRepository.create(departmentName, departmentDescription, userId);
        return departmentRepository.findById(userId, departmentId);
    }

    @Override
    public void updateDepartment(Integer userId, Integer departmentId, Department department) throws EzBadRequestException {
    departmentRepository.update(userId, departmentId, department);
    }

    @Override
    public void removeDepartment(Integer userId, Integer departmentId) throws EzResourceNotFoundException {
    departmentRepository.removeById(userId, departmentId);
    }
}
