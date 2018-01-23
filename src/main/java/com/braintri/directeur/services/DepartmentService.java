package com.braintri.directeur.services;

import com.braintri.directeur.data.Department;
import com.braintri.directeur.data.DepartmentRepository;
import com.braintri.directeur.rest.dtos.DepartmentsDto;
import com.braintri.directeur.rest.dtos.factory.DepartmentDtoFactory;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;
    private DepartmentDtoFactory departmentDtoFactory;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentDtoFactory departmentDtoFactory) {
        this.departmentRepository = departmentRepository;
        this.departmentDtoFactory = departmentDtoFactory;
    }

    public DepartmentsDto getDepartments() {
        List<Department> departmentList = departmentRepository.findAll();
        return departmentDtoFactory.createDepartmentsDto(departmentList);
    }
}
