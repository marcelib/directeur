package com.braintri.directeur.rest.dtos.factory;

import com.braintri.directeur.data.Position;
import com.braintri.directeur.rest.dtos.PositionDto;
import com.braintri.directeur.rest.dtos.PositionsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PositionDtoFactory {

    public PositionsDto createPositionsDto(List<Position> positions) {
        return new PositionsDto(
                positions
                        .stream()
                        .map(this::createPositionDto)
                        .collect(Collectors.toList()));
    }

    public PositionDto createPositionDto(Position position) {
        return new PositionDto(position.getId(), position.getSalary(), position.getPositionName());
    }
}
