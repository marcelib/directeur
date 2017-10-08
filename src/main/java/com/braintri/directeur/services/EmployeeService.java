package com.braintri.directeur.services;

import com.braintri.directeur.rest.command.CreateEmployeeCommand;
import com.braintri.directeur.rest.model.EmployeeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    public List<EmployeeDto> getEmployees() {
        return null;
    }

    public EmployeeDto getEmployee(Long id) {
        return null;
    }

    public void createEmployee( CreateEmployeeCommand command) {

    }

    public void deleteEmployee(Long id) {

    }
}
