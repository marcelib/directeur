package com.wut.directeur.rest.endpoints;

import com.wut.directeur.rest.dtos.*;
import com.wut.directeur.services.EmployeeService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/employees", produces = "application/json")
@Api(value = "/employees", description = "Operations for employees")
public class EmployeesEndpoint {

    private EmployeeService employeeService;

    public EmployeesEndpoint(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @ApiOperation(value = "Get employees", response = EmployeesDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees fetched successfully")})
    public EmployeesDto showAll(@ApiParam(value = "optional filter by name") @RequestParam(value = "name", required = false) String name,
                                @ApiParam(value = "optional filter by surname") @RequestParam(value = "surname", required = false) String surname,
                                @ApiParam(value = "optional filter by email") @RequestParam(value = "email", required = false) String email) {
        EmployeesFilteringDto filteringDto = new EmployeesFilteringDto(name, surname, email);
        return employeeService.getEmployees(filteringDto);
    }

    @PostMapping
    @ApiOperation(value = "Create employee", response = EndpointResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Employee created successfully"),
            @ApiResponse(code = 400, message = "Invalid employee data"),
            @ApiResponse(code = 404, message = "Position does not exist")})
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointResponse create(@RequestBody CreateEmployeeRequestDto requestDto) {
        employeeService.createEmployee(requestDto);

        return new EndpointResponse("Data saved");
    }

    @PutMapping
    @ApiOperation(value = "Update employee", response = EndpointResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee updated successfully"),
            @ApiResponse(code = 400, message = "Invalid employee data"),
            @ApiResponse(code = 404, message = "Position does not exist")})
    public EndpointResponse update(@RequestBody UpdateEmployeeRequestDto requestDto) {
        employeeService.updateEmployee(requestDto);
        return new EndpointResponse("Data saved");
    }

    @GetMapping("/{employeeId}/")
    @ApiOperation(value = "Get employee by Id", response = EmployeeDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee fetched successfully"),
            @ApiResponse(code = 400, message = "Invalid id format"),
            @ApiResponse(code = 404, message = "Employee with requested id not found")})
    public EmployeeDto findOne(@PathVariable Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @DeleteMapping("/{employeeId}/")
    @ApiOperation(value = "Delete employee with specific id", response = EndpointResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid id format"),
            @ApiResponse(code = 404, message = "Employee with requested id not found")})
    public EndpointResponse deleteOne(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new EndpointResponse("Employee deleted successfully");
    }
}
