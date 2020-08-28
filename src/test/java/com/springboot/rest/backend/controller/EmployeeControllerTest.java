package com.springboot.rest.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.springboot.rest.backend.model.Employee;
import com.springboot.rest.backend.security.SecurityConstants;
import com.springboot.rest.backend.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @MockBean
    EmployeeService employeeService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    private String token;

    private Employee employee = new Employee();

    private Long employeeId = 1L;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        employee.setId(employeeId);
        employee.setFirstName("firstname");
        employee.setLastName("lastname");

        String username = "user";
        String password = "password";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/auth?username=" + username + "&password=" + password))
                .andExpect(status().isOk()).andReturn();

        token = result.getResponse().getHeaders(SecurityConstants.TOKEN_HEADER).get(0);

    }

    @Test
    public void givenNoToken_whenAccessResource_thenReturnUnoaterise() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenInvalidToken_whenAccessResource_thenReturnUnoaterise() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .header(SecurityConstants.TOKEN_HEADER, "invalid"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenValidURI_whenGetAllEmployeeSuccess_thenSuccess() throws Exception {
        //Arrange
        List<Employee> allEmployees = new ArrayList<>();
        allEmployees.add(employee);
        doReturn(Lists.newArrayList(employee)).when(employeeService).getAllEmployees();

        //Act
        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees")
                .header(SecurityConstants.TOKEN_HEADER, token));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].first_name", is(employee.getFirstName())))
                .andExpect(jsonPath("$.data[0].last_name", is(employee.getLastName())));
    }

    @Test
    public void givenValidURI_whenGetEmployeeByIdSuccess_thenSuccess() throws Exception {
        //Arrange
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        //Act
        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/" + employeeId)
                .header(SecurityConstants.TOKEN_HEADER, token));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.first_name", is(employee.getFirstName())))
                .andExpect(jsonPath("$.data.last_name", is(employee.getLastName())));
    }

    @Test
    public void givenNotFound_whenGetEmployeeByIdException_thenNotFoundCode() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/" + employeeId)
                .header(SecurityConstants.TOKEN_HEADER, token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("No Record Found.")))
                .andExpect(jsonPath("$.details", is("uri=/api/v1/employees/" + employeeId)));
    }

    @Test
    public void givenValidURI_whenPostEmployeeCreatedSuccess_thenSuccess() throws Exception {
        //Arrange
        given(employeeService.createEmployee(employee)).willReturn(employee);

        //Act and Assert
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees/")
                .header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.first_name", is(employee.getFirstName())))
                .andExpect(jsonPath("$.data.last_name", is(employee.getLastName())));
    }

    @Test
    public void givenValidURI_whenPutEmployeeUpdateByIdSuccess_thenSuccess() throws Exception {
        //Arrange
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));
        given(employeeService.updateEmployee(employee)).willReturn(employee);

        //Act and Assert
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/" + employeeId)
                .header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.first_name", is(employee.getFirstName())))
                .andExpect(jsonPath("$.data.last_name", is(employee.getLastName())));
    }

    @Test
    public void givenNotFound_whenPutEmployeeUpdateByIdException_thenNotFoundCode() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/" + employeeId)
                .header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("No Record Found.")))
                .andExpect(jsonPath("$.details", is("uri=/api/v1/employees/" + employeeId)));
    }

    @Test
    public void givenValidURI_whenDeleteEmployeeUpdateByIdSuccess_thenSuccess() throws Exception {
        //Arrange
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        //Act and Assert
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/employees/" + employeeId)
                .header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata", is("Record id " + employeeId + " is deleted.")));
    }

    @Test
    public void givenNotFound_whenDeleteEmployeeByIdException_thenNotFoundCode() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/employees/" + employeeId)
                .header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("No Record Found.")))
                .andExpect(jsonPath("$.details", is("uri=/api/v1/employees/" + employeeId)));
    }

    @Test
    public void testAdd() throws Exception {
        //add the behavior to throw exception
        doThrow(new RuntimeException("Something wrong."))
                .when(employeeService).getEmployeeById(employeeId);

        //Act and Assert
        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/" + employeeId)
                .header(SecurityConstants.TOKEN_HEADER, token))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message", is("Something wrong.")))
                .andExpect(jsonPath("$.details", is("uri=/api/v1/employees/" + employeeId)));
    }

}