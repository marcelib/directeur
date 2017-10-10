package com.braintri.directeur.services;

import com.braintri.directeur.data.Position;
import com.braintri.directeur.data.PositionRepository;
import com.braintri.directeur.rest.dtos.CreatePositionRequestDto;
import com.braintri.directeur.rest.dtos.PositionDto;
import com.braintri.directeur.rest.dtos.PositionWithEmployeeCountDtoList;
import com.braintri.directeur.rest.dtos.PositionsDto;
import com.braintri.directeur.rest.dtos.factory.PositionDtoFactory;
import com.braintri.directeur.rest.exception.PositionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PositionService {

    private PositionRepository positionRepository;
    private PositionDtoFactory positionDtoFactory;

    public PositionService(PositionRepository positionRepository, PositionDtoFactory positionDtoFactory) {
        this.positionRepository = positionRepository;
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
        Position position = positionRepository.findById(id);
        if (position == null) {
            log.info("No position found with id {0} - position fetching failed");
            throw new PositionNotFoundException();
        }
        return positionDtoFactory.createPositionDto(position);
    }

    public void createPosition(CreatePositionRequestDto requestDto) {
        Position position = new Position();
        position.setPositionName(requestDto.getPositionName());
        position.setSalary(requestDto.getSalary());

        positionRepository.save(position);
    }

    public void deletePosition(Long id) {
        if (!positionRepository.exists(id)) {
            log.info("No position found with id {0} - deleting operation failed");
            throw new PositionNotFoundException();
        }
        positionRepository.delete(id);
    }
}
