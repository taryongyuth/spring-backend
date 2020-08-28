package com.springboot.rest.backend.dto.mapper;

import com.springboot.rest.backend.dto.model.EmployeeDTO;
import com.springboot.rest.backend.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDTO toEmployeeDTO(Employee employee);

    Employee toEmployee(EmployeeDTO employeeDTO);


}
