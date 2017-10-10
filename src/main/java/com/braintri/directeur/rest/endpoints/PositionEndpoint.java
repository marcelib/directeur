package com.braintri.directeur.rest.endpoints;

import com.braintri.directeur.rest.dtos.*;
import com.braintri.directeur.services.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/positions", produces = "application/json")
@Api(value = "/positions", description = "Operations for employee positions")
public class PositionEndpoint {

    private PositionService positionService;

    public PositionEndpoint(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    @ApiOperation(value = "Get positions", response = EmployeesDto.class)
    @ApiResponse(code = 200, message = "Positions fetched successfully")
    public PositionsDto showAll() {
        return positionService.getPositions();
    }

    @PutMapping
    @ApiOperation(value = "Create position", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Position created successfully"),
            @ApiResponse(code = 400, message = "Invalid position data")})
    public SuccessResponse create(@RequestBody CreatePositionRequestDto requestDto) {
        positionService.createPosition(requestDto);
        return new SuccessResponse("Data saved");
    }

    @GetMapping("/{positionId}/")
    @ApiOperation(value = "Get position by Id", response = EmployeeDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Position fetched successfully"),
            @ApiResponse(code = 400, message = "Invalid id format"),
            @ApiResponse(code = 404, message = "Position with requested id not found")})
    public PositionDto findOne(@PathVariable Long positionId) {
        return positionService.getPosition(positionId);
    }

    @DeleteMapping("/{positionId}/")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Position deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid id format"),
            @ApiResponse(code = 404, message = "Position with requested id not found")})
    public SuccessResponse deleteOne(@PathVariable Long positionId) {
        positionService.deletePosition(positionId);
        return new SuccessResponse("Position deleted successfully");
    }
}
