package com.braintri.directeur.rest.dtos.factory;

import com.braintri.directeur.data.Department;
import com.braintri.directeur.rest.dtos.DepartmentDto;
import com.braintri.directeur.rest.dtos.DepartmentsDto;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DepartmentDtoFactory {

    public DepartmentsDto createDepartmentsDto(List<Department> departments) {
        return new DepartmentsDto(
                departments.stream()
                        .map(this::createDepartmentDto)
                        .collect(Collectors.toList()));
    }

    public DepartmentDto createDepartmentDto(Department department) {
        return new DepartmentDto(department.getId(), department.getDepartmentName());
    }
}
