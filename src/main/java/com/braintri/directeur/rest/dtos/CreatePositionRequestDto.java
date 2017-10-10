package com.braintri.directeur.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePositionRequestDto {

    private Long salary;
    private String positionName;
}