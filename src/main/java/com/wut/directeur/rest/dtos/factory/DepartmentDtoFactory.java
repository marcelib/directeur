package com.wut.directeur.rest.dtos.factory;

import com.wut.directeur.data.model.Department;
import com.wut.directeur.data.model.Employee;
import com.wut.directeur.rest.dtos.department.DepartmentDto;
import com.wut.directeur.rest.dtos.department.DepartmentsDto;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        return new DepartmentDto(department.getId(), department.getDepartmentName(), department.getDepartmentDescription(),
                getDirectorName(department.getDirectors()));
    }

    private String getDirectorName(List<Employee> directors){
        if(directors.size() > 0){
            return directors.get(0).name + ' ' + directors.get(0).surname;
        } else return " ";
    }
}
