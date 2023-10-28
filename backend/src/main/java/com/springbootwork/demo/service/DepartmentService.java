package com.springbootwork.demo.service;

import com.springbootwork.demo.entity.Department;
import com.springbootwork.demo.error.DepartmentNotFoundException;

import java.util.List;

public interface DepartmentService {
    public Department saveDepartment(Department department);

    public List<Department> getDepartment();

    public Department getDepartmentById(Long dpeartmentId) throws DepartmentNotFoundException;

    public void deleteDepartmentById(Long departmentId);

    public Department updateDepartmentById(Long departmentId, Department department);

    public Department getDepartmentByName(String departmentName);
}
