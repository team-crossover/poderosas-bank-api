package com.crossover.poderosasbank.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferirDto {

    @NotNull
    @Min(0)
    private Double valor;

    @NotNull
    private DadosContaBancariaDto contaPagante;

    @NotNull
    private DadosContaBancariaDto contaRecebedora;

}
