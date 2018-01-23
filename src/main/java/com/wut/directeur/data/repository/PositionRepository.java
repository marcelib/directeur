package com.wut.directeur.data.repository;

import com.wut.directeur.data.model.Position;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Position findById(long positionId);

    List<Position> findAll();
}
