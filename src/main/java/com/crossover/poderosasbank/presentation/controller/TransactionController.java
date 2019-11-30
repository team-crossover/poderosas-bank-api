package com.crossover.poderosasbank.presentation.controller;

import com.crossover.poderosasbank.business.service.TransactionService;
import com.crossover.poderosasbank.presentation.dto.PagarDto;
import com.crossover.poderosasbank.presentation.dto.TransferirDto;
import com.crossover.poderosasbank.presentation.dto.results.ResultadoPagamentoDto;
import com.crossover.poderosasbank.presentation.dto.results.ResultadoTransferenciaDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/api/v1/transacoes/pagar")
    @ApiOperation("Efetua um pagamento de um cartão de crédito para uma conta bancária")
    private ResultadoPagamentoDto pagar(@RequestBody @Valid PagarDto pagarDto) {
        return transactionService.pay(pagarDto);
    }

    @PostMapping("/api/v1/transacoes/transferir")
    @ApiOperation("Transfere valor de uma conta bancária para outra")
    private ResultadoTransferenciaDto transferir(@RequestBody @Valid TransferirDto transferirDto) {
        return transactionService.transfer(transferirDto);
    }

}
