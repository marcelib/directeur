package com.wut.directeur.data.repository;

import com.wut.directeur.data.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findById(long roleId);

    List<Role> findAll();
}
