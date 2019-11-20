package com.crossover.poderosasbank.presentation.dto;

import com.crossover.poderosasbank.business.entity.Usuario;
import com.crossover.poderosasbank.business.enums.TipoConta;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidarContaBancariaDto {

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
