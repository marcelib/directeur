package com.wut.directeur.rest.dtos;

import com.wut.directeur.data.Position;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDto {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private Position position;
}
