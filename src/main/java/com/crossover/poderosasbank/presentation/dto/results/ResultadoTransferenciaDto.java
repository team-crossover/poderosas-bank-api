package com.crossover.poderosasbank.presentation.dto.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoTransferenciaDto {

    public boolean realizado;

    public BigDecimal totalTransferido;

    public BigDecimal novoSaldoPagante;

    public BigDecimal novoSaldoRecebedor;

}
