package com.wut.directeur.services;

import com.wut.directeur.data.model.Department;
import com.wut.directeur.data.repository.DepartmentRepository;
import com.wut.directeur.rest.dtos.department.CreateDepartmentRequestDto;
import com.wut.directeur.rest.dtos.department.DepartmentDto;
import com.wut.directeur.rest.dtos.department.DepartmentsDto;
import com.wut.directeur.rest.dtos.department.UpdateDepartmentRequestDto;
import com.wut.directeur.rest.dtos.factory.DepartmentDtoFactory;
import com.wut.directeur.rest.exception.DepartmentNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

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

    public DepartmentDto getDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId);

        throwIfDepartmentNotFound(department, departmentId);
        return departmentDtoFactory.createDepartmentDto(department);
    }

    @Transactional
    public void createDepartment(CreateDepartmentRequestDto requestDto) {
        Department department = new Department();
        department.setDepartmentDescription(requestDto.getDepartmentDescription());
        department.setDepartmentName(requestDto.getDepartmentName());
        departmentRepository.save(department);
    }

    @Transactional
    public void updateDepartment(UpdateDepartmentRequestDto requestDto) {
        Department department = departmentRepository.findById(requestDto.getId());
        throwIfDepartmentNotFound(department, requestDto.getId());
        department.setDepartmentDescription(requestDto.getDepartmentDescription());
        department.setDepartmentName(requestDto.getDepartmentName());
        departmentRepository.save(department);
    }

    public void deleteDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId);
        throwIfDepartmentNotFound(department, departmentId);
        departmentRepository.delete(department);
    }

    private void throwIfDepartmentNotFound(Department department, Long departmentId) {
        if (department == null) {
            log.info("No department found with id {}", departmentId);
            throw new DepartmentNotFoundException();
        }
    }
}
