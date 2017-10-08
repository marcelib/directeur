package com.braintri.directeur.rest.endpoints;


import com.braintri.directeur.rest.command.CreateEmployeeCommand;
import com.braintri.directeur.rest.model.EmployeeDto;
import com.braintri.directeur.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesEndpoint {


    private EmployeeService employeeService;

    public EmployeesEndpoint(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    List<EmployeeDto> showAll() {
        return employeeService.getEmployees();
    }

    @GetMapping("/{employeeId}/")
    EmployeeDto findOne(@PathVariable Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PutMapping
    void create(@RequestBody CreateEmployeeCommand command) {
        employeeService.createEmployee(command);
    }

    @DeleteMapping("/{employeeId}/")
    void deleteOne(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}
