package com.wut.directeur.rest.endpoints;

import com.wut.directeur.rest.dtos.*;
import com.wut.directeur.services.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/positions", produces = "application/json")
@Api(value = "/positions", description = "Operations for employee positions")
public class PositionsEndpoint {

    private PositionService positionService;

    public PositionsEndpoint(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    @ApiOperation(value = "Get positions", response = PositionsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Positions fetched successfully")})
    public PositionsDto showAll() {
        return positionService.getPositions();
    }

    @GetMapping("/withEmployeeCount/")
    @ApiOperation(value = "Get positions with employee count", response = PositionWithEmployeeCountDtoList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Data fetched successfully")})
    public PositionWithEmployeeCountDtoList showAllWithEmployeeCount() {
        return positionService.getPositionsWithEmployeeCount();
    }

    @PostMapping
    @ApiOperation(value = "Create position", response = EndpointResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Position created successfully"),
            @ApiResponse(code = 400, message = "Invalid position data")})
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointResponse create(@RequestBody CreatePositionRequestDto requestDto) {
        positionService.createPosition(requestDto);
        return new EndpointResponse("Data saved");
    }

    @PutMapping
    @ApiOperation(value = "Update position", response = EndpointResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Position updated successfully"),
            @ApiResponse(code = 400, message = "Invalid position data")})
    public EndpointResponse update(@RequestBody UpdatePositionRequestDto requestDto) {
        positionService.updatePosition(requestDto);
        return new EndpointResponse("Data saved");
    }

    @GetMapping("/{positionId}/")
    @ApiOperation(value = "Get position by Id", response = PositionDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Position fetched successfully"),
            @ApiResponse(code = 400, message = "Invalid id format"),
            @ApiResponse(code = 404, message = "Position with requested id not found")})
    public PositionDto findOne(@PathVariable Long positionId) {
        return positionService.getPosition(positionId);
    }

    @DeleteMapping("/{positionId}/")
    @ApiOperation(value = "Delete position with specific Id", response = EndpointResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Position deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid id format"),
            @ApiResponse(code = 404, message = "Position with requested id not found")})
    public EndpointResponse deleteOne(@PathVariable Long positionId) {
        positionService.deletePosition(positionId);
        return new EndpointResponse("Position deleted successfully");
    }
}
