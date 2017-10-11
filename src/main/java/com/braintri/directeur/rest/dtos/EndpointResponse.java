package com.braintri.directeur.rest.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("success")
public class EndpointResponse {

    @ApiModelProperty("status")
    private String status = "OK";

    @ApiModelProperty("message")
    private String message;

    public EndpointResponse(String message) {
        this.message = message;
    }
}
