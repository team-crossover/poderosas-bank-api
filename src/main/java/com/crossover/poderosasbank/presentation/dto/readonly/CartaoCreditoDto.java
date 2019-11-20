package com.crossover.poderosasbank.presentation.dto.readonly;

import com.crossover.poderosasbank.business.entity.CartaoCredito;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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

    public static CartaoCreditoDto fromCartao(CartaoCredito cartaoCredito) {
        return CartaoCreditoDto.builder()
                .id(cartaoCredito.getId())
                .idUsuario(cartaoCredito.getContaBancaria() == null ? null : cartaoCredito.getContaBancaria().getUsuario().getId())
                .idContaBancaria(cartaoCredito.getContaBancaria() == null ? null : cartaoCredito.getContaBancaria().getId())
                .numero(cartaoCredito.getNumero())
                .validade(cartaoCredito.getValidade())
                .cvv(cartaoCredito.getCvv())
                .build();
    }
}
