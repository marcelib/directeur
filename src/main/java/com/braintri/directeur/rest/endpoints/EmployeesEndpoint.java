package com.braintri.directeur.rest.endpoints;

import com.braintri.directeur.rest.dtos.CreateEmployeeRequestDto;
import com.braintri.directeur.rest.dtos.EmployeeDto;
import com.braintri.directeur.rest.dtos.EmployeesDto;
import com.braintri.directeur.rest.dtos.SuccessResponse;
import com.braintri.directeur.services.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiResponse(code = 200, message = "Employees fetched successfully")
    public EmployeesDto showAll() {
        return employeeService.getEmployees();
    }

    @PutMapping
    @ApiOperation(value = "Create employee", response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee created successfully"),
            @ApiResponse(code = 400, message = "Invalid employee data"),
            @ApiResponse(code = 404, message = "Position does not exist")})
    public SuccessResponse create(@RequestBody CreateEmployeeRequestDto requestDto) {
        employeeService.createEmployee(requestDto);
        return new SuccessResponse("Data saved");
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
    @ApiOperation(value = "Delete employee with specific id", response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid id format"),
            @ApiResponse(code = 404, message = "Employee with requested id not found")})
    public SuccessResponse deleteOne(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new SuccessResponse("Employee deleted successfully");
    }
}
