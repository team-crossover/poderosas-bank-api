package com.crossover.poderosasbank.presentation.dto.readonly;

import com.crossover.poderosasbank.business.entity.CartaoCredito;
import com.crossover.poderosasbank.business.entity.ContaBancaria;
import com.crossover.poderosasbank.business.enums.TipoConta;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {})
public class ContaBancariaDto {

    private Long id;

    private Long idUsuario;

    private List<Long> idsCartoesCredito;

    @ApiModelProperty(example = "CORRENTE")
    private TipoConta tipo;

    @ApiModelProperty(example = "01234")
    private String numero;

    @ApiModelProperty(example = "5")
    private String digito;

    @ApiModelProperty(example = "6789")
    private String agencia;

    public static ContaBancariaDto fromConta(ContaBancaria contaBancaria) {
        return ContaBancariaDto.builder()
                .id(contaBancaria.getId())
                .idUsuario(contaBancaria.getUsuario().getId())
                .idsCartoesCredito(contaBancaria.getCartoesCredito().stream().map(CartaoCredito::getId).collect(Collectors.toList()))
                .tipo(contaBancaria.getTipo())
                .numero(contaBancaria.getNumero())
                .digito(contaBancaria.getDigito())
                .agencia(contaBancaria.getAgencia())
                .build();
    }
}
