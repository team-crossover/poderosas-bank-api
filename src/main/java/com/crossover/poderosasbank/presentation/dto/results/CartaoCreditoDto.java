package com.crossover.poderosasbank.presentation.dto.results;

import com.crossover.poderosasbank.business.entity.CartaoCredito;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {})
public class CartaoCreditoDto {

    private Long id;

    private Long idUsuario;

    private Long idContaBancaria;

    @ApiModelProperty(example = "5555666677778888")
    private String numero;

    @ApiModelProperty(example = "20/2020")
    private String validade;

    @ApiModelProperty(example = "999")
    private String cvv;

    private BigDecimal faturaAtual;

    private BigDecimal limite;

    public static CartaoCreditoDto fromCartao(CartaoCredito cartaoCredito) {
        return CartaoCreditoDto.builder()
                .id(cartaoCredito.getId())
                .idUsuario(cartaoCredito.getContaBancaria() == null ? null : cartaoCredito.getContaBancaria().getUsuario().getId())
                .idContaBancaria(cartaoCredito.getContaBancaria() == null ? null : cartaoCredito.getContaBancaria().getId())
                .numero(cartaoCredito.getNumero())
                .validade(cartaoCredito.getValidade())
                .cvv(cartaoCredito.getCvv())
                .faturaAtual(cartaoCredito.getFaturaAtual())
                .limite(cartaoCredito.getLimite())
                .build();
    }
}
