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
public class DadosContaBancariaDto {

    @NotBlank
    @ApiModelProperty(example = "01234")
    private String numero;

    @NotBlank
    @ApiModelProperty(example = "5")
    private String digito;

    @NotBlank
    @ApiModelProperty(example = "6789")
    private String agencia;

}
