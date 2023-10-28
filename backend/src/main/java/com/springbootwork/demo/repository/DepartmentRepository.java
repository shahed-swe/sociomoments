package com.springbootwork.demo.repository;

import com.springbootwork.demo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    public Department findByDepartmentName(String departmentname);

//    for custom query
//    @Query() -> for custom query
    public Department findByDepartmentNameIgnoreCase(String departmentname);
}
