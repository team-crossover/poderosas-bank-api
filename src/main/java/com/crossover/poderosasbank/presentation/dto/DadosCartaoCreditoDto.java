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
public class DadosCartaoCreditoDto {

    @NotBlank
    @ApiModelProperty(example = "5555666677778888")
    private String numero;

    @NotBlank
    @ApiModelProperty(example = "20/2020")
    private String validade;

    @NotBlank
    @ApiModelProperty(example = "999")
    private String cvv;

}
