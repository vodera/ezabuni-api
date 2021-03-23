package com.turubini.ezabuni.resources;


import com.turubini.ezabuni.domain.Department;
import com.turubini.ezabuni.services.DepartmentService.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentResource {

    @Autowired
    DepartmentServiceImpl departmentService;

    @GetMapping("")
    public ResponseEntity<List<Department>> getAllDepartments(HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("userId");
        List<Department> departments = departmentService.fetchAllDepartmentsByUserId(userId);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> getDepartmentsById(HttpServletRequest request,
                                                              @PathVariable("departmentId") Integer departmentId) {
        int userId = (Integer) request.getAttribute("userId");
        Department department = departmentService.fetchDepartmentById(userId, departmentId);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Department> addDepartment(HttpServletRequest request,
                                                          @RequestBody Map<String, Object> departmentMap) {

        String departmentName = (String) departmentMap.get("departmentName");
        String departmentDescription = (String) departmentMap.get("departmentDescription");
        int userId = (Integer) request.getAttribute("userId");
        Department department = departmentService.addDepartment(departmentName, departmentDescription, userId);

        return  new ResponseEntity<>(department, HttpStatus.CREATED);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<Map<String, Boolean>> updateDepartment(HttpServletRequest request,
                                                                    @PathVariable("departmentId") Integer departmentId,
                                                                    @RequestBody Department department) {
        int userId = (Integer) request.getAttribute("userId");
        departmentService.updateDepartment(userId, departmentId, department);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Map<String, Boolean>> deleteDepartment(HttpServletRequest request,
                                                                    @PathVariable("departmentId") Integer departmentId) {
        int userId = (Integer) request.getAttribute("userId");
        departmentService.removeDepartment(userId, departmentId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}
