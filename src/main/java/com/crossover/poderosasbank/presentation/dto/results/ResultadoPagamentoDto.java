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
public class ResultadoPagamentoDto {

    public BigDecimal totalPago;

    public BigDecimal novaFaturaPagante;

    public BigDecimal novoSaldoRecebedor;

}
