package com.springbootwork.demo.service;

import com.springbootwork.demo.entity.Department;
import com.springbootwork.demo.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        Department department = Department.builder()
                .departmentName("SWE")
                .departmentAddress("Tangail")
                .departmentCode("TWS")
                .build();

        Mockito.when(departmentRepository.findByDepartmentNameIgnoreCase("SWE")).thenReturn(department);
    }

    @Test
    @DisplayName("Get Data Based on Valid Department Name")
//    @Disabled -> for disablling test case
    public void whenValidDepartmentName_thenDepartShouldFound(){
        String departmentName = "SWE";
        Department found = departmentService.getDepartmentByName(departmentName);

        assertEquals(departmentName, found.getDepartmentName());


    }
}