package com.braintri.directeur.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findById(long positionId);

    List<Role> findAll();
}
