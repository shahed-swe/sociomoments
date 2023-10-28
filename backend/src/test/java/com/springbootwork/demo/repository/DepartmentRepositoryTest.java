package com.springbootwork.demo.repository;

import com.springbootwork.demo.entity.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        Department department = Department.builder()
                .departmentName("CSE")
                .departmentCode("MAND")
//                .departmentId(1L)
                .departmentAddress("MONTING")
                .build();

        entityManager.persist(department);
    }


    @Test
    @DisplayName("Get Find by id successfully")
    public void whenFindById_thenReturnDepartment(){
        Department found = departmentRepository.findById(1L).get();
        assertEquals(found.getDepartmentName(), "CSE");
    }

}