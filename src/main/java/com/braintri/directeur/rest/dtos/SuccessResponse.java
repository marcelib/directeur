package com.braintri.directeur.rest.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("success")
public class SuccessResponse {

    @ApiModelProperty("status")
    private String status = "OK";

    @ApiModelProperty("message")
    private String message;

    public SuccessResponse(String message) {
        this.message = message;
    }
}
