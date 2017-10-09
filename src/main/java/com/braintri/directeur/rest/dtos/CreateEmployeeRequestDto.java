package com.braintri.directeur.rest.dtos;

import com.braintri.directeur.data.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRequestDto {

    private String name;
    private String surname;
    private String email;
    private Position position;
}
