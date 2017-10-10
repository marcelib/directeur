package com.braintri.directeur.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeesFilteringDto {

    private String name;
    private String surname;
    private String email;
}
