package com.braintri.directeur.services;

import com.braintri.directeur.data.Position;
import com.braintri.directeur.data.PositionRepository;
import com.braintri.directeur.rest.dtos.*;
import com.braintri.directeur.rest.dtos.factory.PositionDtoFactory;
import com.braintri.directeur.rest.exception.PositionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        throwIfPositionNotFound(id);

        Position position = positionRepository.findById(id);
        return positionDtoFactory.createPositionDto(position);
    }

    @Transactional
    public void createPosition(CreatePositionRequestDto requestDto) {
        Position position = new Position();
        position.setPosition_name(requestDto.getPositionName());
        position.setMin_salary(requestDto.getSalary());

        positionRepository.save(position);
    }

    @Transactional
    public void updatePosition(UpdatePositionRequestDto positionDto) {
        throwIfPositionNotFound(positionDto.getId());

        Position position = positionRepository.findById(positionDto.getId());
        position.setMin_salary(positionDto.getSalary());
        position.setPosition_name(positionDto.getPositionName());
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
}
