package com.wut.directeur.services;

import com.wut.directeur.data.Department;
import com.wut.directeur.data.DepartmentRepository;
import com.wut.directeur.data.Position;
import com.wut.directeur.data.PositionRepository;
import com.wut.directeur.data.Role;
import com.wut.directeur.data.RoleRepository;
import com.wut.directeur.rest.dtos.*;
import com.wut.directeur.rest.dtos.factory.PositionDtoFactory;
import com.wut.directeur.rest.exception.DepartmentNotFoundException;
import com.wut.directeur.rest.exception.PositionNotFoundException;
import com.wut.directeur.rest.exception.RoleNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

@Slf4j
@Service
public class PositionService {

    private PositionRepository positionRepository;
    private DepartmentRepository departmentRepository;
    private RoleRepository roleRepository;
    private PositionDtoFactory positionDtoFactory;

    public PositionService(PositionRepository positionRepository, DepartmentRepository departmentRepository, RoleRepository roleRepository, PositionDtoFactory positionDtoFactory) {
        this.positionRepository = positionRepository;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
        this.positionDtoFactory = positionDtoFactory;
    }

    public PositionWithEmployeeCountDtoList getPositionsWithEmployeeCount() {
        List<Position> positionList = positionRepository.findAll();
        return positionDtoFactory.createPositionWithEmployeeCountDtoList(positionList);
    }

    public PositionsDto getPositions() {
        List<Position> positionList = positionRepository.findAll();
        return positionDtoFactory.createPositionsDto(positionList);
    }

    public PositionDto getPosition(Long id) {
        throwIfPositionNotFound(id);

        Position position = positionRepository.findById(id);
        return positionDtoFactory.createPositionDto(position);
    }

    @Transactional
    public void createPosition(CreatePositionRequestDto requestDto) {
        Department department = departmentRepository.findById(requestDto.getDepartmentId());
        Role role = roleRepository.findById(requestDto.getRoleId());

        throwIfRolenNotFound(role, requestDto.getRoleId());
        throwIfDepartmentNotFound(department,requestDto.getDepartmentId());

        Position position = new Position();
        position.setPositionName(requestDto.getPositionName());
        position.setMinSalary(requestDto.getSalary());
        position.setDepartment(department);
        position.setRole(role);

        positionRepository.save(position);
    }

    @Transactional
    public void updatePosition(UpdatePositionRequestDto requestDto) {
        Department department = departmentRepository.findById(requestDto.getDepartmentId());
        Role role = roleRepository.findById(requestDto.getRoleId());

        throwIfRolenNotFound(role, requestDto.getRoleId());
        throwIfDepartmentNotFound(department,requestDto.getDepartmentId());

        throwIfPositionNotFound(requestDto.getId());

        Position position = new Position();
        position.setPositionName(requestDto.getPositionName());
        position.setMinSalary(requestDto.getSalary());
        position.setDepartment(department);
        position.setRole(role);

        positionRepository.save(position);
    }

    @Transactional
    public void deletePosition(Long id) {
        throwIfPositionNotFound(id);
        positionRepository.delete(id);
    }

    private void throwIfPositionNotFound(Long positionId) {
        if (!positionRepository.exists(positionId)) {
            log.info("No position found with id {}", positionId);
            throw new PositionNotFoundException();
        }
    }


    private void throwIfDepartmentNotFound(Department department, Long departmentId) {
        if (department == null) {
            log.info("No department found with id {}", departmentId);
            throw new DepartmentNotFoundException();
        }
    }

    private void throwIfRolenNotFound(Role role, Long roleId) {
        if (role == null) {
            log.info("No role found with id {}", roleId);
            throw new RoleNotFoundException();
        }
    }
}
