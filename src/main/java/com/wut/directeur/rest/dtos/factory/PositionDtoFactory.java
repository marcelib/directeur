package com.wut.directeur.rest.dtos.factory;

import com.wut.directeur.data.repository.EmployeeRepository;
import com.wut.directeur.data.model.Position;
import com.wut.directeur.rest.dtos.position.PositionDto;
import com.wut.directeur.rest.dtos.position.PositionWithEmployeeCountDto;
import com.wut.directeur.rest.dtos.position.PositionWithEmployeeCountDtoList;
import com.wut.directeur.rest.dtos.position.PositionsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PositionDtoFactory {

    private EmployeeRepository employeeRepository;

    public PositionDtoFactory(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public PositionWithEmployeeCountDtoList createPositionWithEmployeeCountDtoList(List<Position> positions) {
        return new PositionWithEmployeeCountDtoList(positions
                .stream()
                .map(this::createPositionWithEmployeeCount)
                .collect(Collectors.toList()));
    }

    private PositionWithEmployeeCountDto createPositionWithEmployeeCount(Position position) {
        return new PositionWithEmployeeCountDto(createPositionDto(position), employeeRepository.countByPosition(position));
    }

    public PositionsDto createPositionsDto(List<Position> positions) {
        return new PositionsDto(
                positions
                        .stream()
                        .map(this::createPositionDto)
                        .collect(Collectors.toList()));
    }

    public PositionDto createPositionDto(Position position) {
        return new PositionDto(position.getId(), position.getMinSalary(), position.getPositionName());
    }
}
