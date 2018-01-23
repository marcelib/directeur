package com.wut.directeur.rest.dtos.factory;

import com.wut.directeur.data.Role;
import com.wut.directeur.rest.dtos.RoleDto;
import com.wut.directeur.rest.dtos.RolesDto;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RoleDtoFactory {

    public RolesDto createRolesDto(List<Role> roles) {
        return new RolesDto(
                roles.stream()
                        .map(this::createRoleDto)
                        .collect(Collectors.toList()));
    }

    public RoleDto createRoleDto(Role role) {
        return new RoleDto(role.getId(), role.getName(), role.isAdmin(), role.isAccountant(), role.isNormal());
    }
}
