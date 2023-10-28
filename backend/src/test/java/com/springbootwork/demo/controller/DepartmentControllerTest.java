package com.springbootwork.demo.controller;

import com.springbootwork.demo.entity.Department;
import com.springbootwork.demo.error.DepartmentNotFoundException;
import com.springbootwork.demo.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = Department.builder()
                .departmentAddress("Tangail")
                .departmentName("SWE")
                .departmentCode("SWE")
                .departmentId(1L)
                .build();


    }

    @Test
    @DisplayName("Saving Department")
    void saveDepartment() throws Exception {
        Department inputDept = Department.builder()
                .departmentAddress("Tangail")
                .departmentName("CSE")
                .departmentCode("MAKE")
                .build();

        Mockito.when(departmentService.saveDepartment(inputDept)).thenReturn(department);
        mockMvc.perform(post("/departmentssave").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                "    \"departmentCode\":\"MAKE\",\n" +
                "    \"departmentName\":\"SWE\",\n" +
                "    \"departmentAddress\":\"Tangail\"\n" +
                "}")).andExpect(status().isOk());
    }

    @Test
    void fetchDepartmentById() throws Exception {
        Mockito.when(departmentService.getDepartmentById(1L)).thenReturn(department);


        mockMvc.perform(get("/department/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departmentName").value(department.getDepartmentName()));
    }
}