package com.springboot.rest.backend.controller.v1.api;

import com.springboot.rest.backend.controller.v1.response.Response;
import com.springboot.rest.backend.dto.mapper.EmployeeMapper;
import com.springboot.rest.backend.dto.model.EmployeeDTO;
import com.springboot.rest.backend.exception.NotFoundException;
import com.springboot.rest.backend.model.Employee;
import com.springboot.rest.backend.services.EmployeeService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/api/v1/employees")
@Api(tags = "API")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping
    public Response getAllEmployees() {
        logger.info("GET API called for get all employees data");
        return Response.ok().setData(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public Response getEmployeeById(@PathVariable(value = "id") Long employeeId)
            throws NotFoundException {
        logger.info("GET API called for id : " + employeeId);
        Employee employee = employeeService.getEmployeeById(employeeId)
                .orElseThrow(() -> new NotFoundException());
        return Response.ok().setData(employee);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public Response createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        logger.info("POST API called for create new employee");
        Employee employee = employeeMapper.toEmployee((employeeDTO));
        return Response.ok().setData(employeeService.createEmployee(employee));
    }

    @PutMapping("/{id}")
    public Response updateEmployeeById(@PathVariable(value = "id") Long employeeId,
                                       @Valid @RequestBody EmployeeDTO employeeDTO) throws NotFoundException {
        logger.info("PUT API called for update existing employee id: " + employeeId);

        employeeService.getEmployeeById(employeeId)
                .orElseThrow(() -> new NotFoundException());

        Employee employee = employeeMapper.toEmployee(employeeDTO);
        employee.setId(employeeId);
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return Response.ok().setData(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public Response deleteEmployeeById(@PathVariable(value = "id") Long employeeId)
            throws NotFoundException {
        logger.info("DELETE API called for delete employee id: " + employeeId);

        Employee employee = employeeService.getEmployeeById(employeeId)
                .orElseThrow(() -> new NotFoundException());
        employeeService.deleteEmployeeById(employee.getId());
        return Response.ok().setMetadata("Record id " + employeeId + " is deleted.");
    }


}