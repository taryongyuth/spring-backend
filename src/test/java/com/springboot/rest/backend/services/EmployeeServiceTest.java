package com.springboot.rest.backend.services;

import com.springboot.rest.backend.model.Employee;
import com.springboot.rest.backend.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeService service;

    private Employee employee = new Employee();
    private Long id = 1l;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        employee.setId(1L);
        employee.setFirstName("firstname");
        employee.setLastName("lastname");
    }

    @Test
    public void testGetAllEmployees() {
        //given
        List<Employee> employees = new ArrayList();
        employees.add(employee);
        given(repository.findAll()).willReturn(employees);

        //when
        List<Employee> result = service.getAllEmployees();

        //then
        assertThat(result).hasSize(1);
        assertEmployeeFields(result.get(0));
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetEmployeeById() {
        //given
        given(repository.findById(id)).willReturn(Optional.of(employee));

        //when
        Optional<Employee> result = service.getEmployeeById(id);

        //then
        assertThat(result.isPresent()).isTrue();
        assertEmployeeFields(result.orElseGet(null));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void testGetCreateEmployee() {
        //given
        given(repository.save(employee)).willReturn(employee);

        //when
        Employee result = service.createEmployee(employee);

        //then
        assertEmployeeFields(result);
        verify(repository, times(1)).save(employee);
    }

    @Test
    public void testGetUpdateEmployee() {
        //given
        given(repository.save(employee)).willReturn(employee);

        //when
        Employee result = service.updateEmployee(employee);

        //then
        assertEmployeeFields(result);
        verify(repository, times(1)).save(employee);
    }

    @Test
    public void testDeleteUpdateEmployee() {
        //when
        service.deleteEmployeeById(id);

        //then
        verify(repository, times(1)).deleteById(id);
    }

    //verify that Employee object have same fields
    private void assertEmployeeFields(Employee employee) {
        assertThat(employee.getId()).isInstanceOf(Long.class);
        assertThat(employee.getId()).isEqualTo(1L);
        assertThat(employee.getFirstName()).isEqualTo("firstname");
        assertThat(employee.getLastName()).isEqualTo("lastname");
    }
}