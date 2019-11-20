package com.crossover.poderosasbank.presentation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotBlank
    @ApiModelProperty(example = "conta")
    private String username;

    @NotBlank
    @ApiModelProperty(example = "123")
    private String senha;

}
