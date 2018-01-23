package com.braintri.directeur.services;

import com.braintri.directeur.data.Role;
import com.braintri.directeur.data.RoleRepository;
import com.braintri.directeur.rest.dtos.RolesDto;
import com.braintri.directeur.rest.dtos.factory.RoleDtoFactory;

import org.springframework.stereotype.Service;

import java.util.List;

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
}
