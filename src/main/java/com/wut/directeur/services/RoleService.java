package com.wut.directeur.services;

import com.wut.directeur.data.model.Role;
import com.wut.directeur.data.repository.RoleRepository;
import com.wut.directeur.rest.dtos.role.CreateRoleRequestDto;
import com.wut.directeur.rest.dtos.role.RoleDto;
import com.wut.directeur.rest.dtos.role.RolesDto;
import com.wut.directeur.rest.dtos.role.UpdateRoleRequestDto;
import com.wut.directeur.rest.dtos.factory.RoleDtoFactory;
import com.wut.directeur.rest.exception.RoleNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleService {

    private RoleRepository roleRepository;
    private RoleDtoFactory roleDtoFactory;

    public RoleService(RoleRepository roleRepository, RoleDtoFactory roleDtoFactory) {
        this.roleRepository = roleRepository;
        this.roleDtoFactory = roleDtoFactory;
    }


    public RolesDto getRoles() {
        List<Role> roleList = roleRepository.findAll();
        return roleDtoFactory.createRolesDto(roleList);
    }

    public RoleDto getRole(Long roleId) {
        Role role = roleRepository.findById(roleId);

        throwIfRoleNotFound(role, roleId);
        return roleDtoFactory.createRoleDto(role);
    }

    @Transactional
    public void createRole(CreateRoleRequestDto requestDto) {
        Role role = new Role();
        role.setDescription(requestDto.getDescription());
        role.setName(requestDto.getName());
        role.setNormal(requestDto.isNormal());
        role.setAccountant(requestDto.isAccountant());
        role.setAdmin(requestDto.isAdmin());
        roleRepository.save(role);
    }

    @Transactional
    public void updateRole(UpdateRoleRequestDto requestDto) {
        Role role = roleRepository.findById(requestDto.getId());
        throwIfRoleNotFound(role, requestDto.getId());
        role.setDescription(requestDto.getDescription());
        role.setName(requestDto.getName());
        role.setNormal(requestDto.isNormal());
        role.setAccountant(requestDto.isAccountant());
        role.setAdmin(requestDto.isAdmin());
        roleRepository.save(role);

    }

    public void deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId);
        throwIfRoleNotFound(role, roleId);
        roleRepository.delete(role);
    }


    private void throwIfRoleNotFound(Role role, Long roleId) {
        if (role == null) {
            log.info("No role found with id {}", roleId);
            throw new RoleNotFoundException();
        }
    }
}
