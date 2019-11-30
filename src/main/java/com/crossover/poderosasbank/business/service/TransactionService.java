package com.crossover.poderosasbank.business.service;

import com.crossover.poderosasbank.business.entity.CartaoCredito;
import com.crossover.poderosasbank.business.entity.ContaBancaria;
import com.crossover.poderosasbank.presentation.dto.PagarDto;
import com.crossover.poderosasbank.presentation.dto.TransferirDto;
import com.crossover.poderosasbank.presentation.dto.results.ResultadoPagamentoDto;
import com.crossover.poderosasbank.presentation.dto.results.ResultadoTransferenciaDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Log4j2
@Service
@Transactional
public class TransactionService {

    @Autowired
    private CartaoCreditoService cartaoCreditoService;

    @Autowired
    private ContaBancariaService contaBancariaService;

    public ResultadoPagamentoDto pay(PagarDto payDto) {
        log.info("pay " + payDto);

        CartaoCredito cartaoPagante = cartaoCreditoService.findByDadosAndValidate(payDto.getCartaoPagante());
        ContaBancaria contaRecebedor = contaBancariaService.findByDadosAndValidate(payDto.getContaRecebedora());
        BigDecimal valor = new BigDecimal(payDto.getValor());

        BigDecimal limiteDisponivel = cartaoPagante.getLimite().subtract(cartaoPagante.getFaturaAtual());
        if (valor.compareTo(limiteDisponivel) > 0)
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Cartao de credito não tem limite suficiente");

        BigDecimal novaFatura = cartaoPagante.getFaturaAtual().add(valor);
        cartaoPagante.setFaturaAtual(novaFatura);
        cartaoCreditoService.save(cartaoPagante);

        BigDecimal novoSaldo = contaRecebedor.getSaldo().add(valor);
        contaRecebedor.setSaldo(novoSaldo);
        contaBancariaService.save(contaRecebedor);

        return ResultadoPagamentoDto.builder()
                .totalPago(valor.setScale(2, RoundingMode.HALF_EVEN))
                .novaFaturaPagante(novaFatura.setScale(2, RoundingMode.HALF_EVEN))
                .novoSaldoRecebedor(novoSaldo.setScale(2, RoundingMode.HALF_EVEN))
                .build();
    }

    public ResultadoTransferenciaDto transfer(TransferirDto transferirDto) {
        ContaBancaria contaPagante = contaBancariaService.findByDadosAndValidate(transferirDto.getContaPagante());
        ContaBancaria contaRecebedor = contaBancariaService.findByDadosAndValidate(transferirDto.getContaRecebedora());
        BigDecimal valor = new BigDecimal(transferirDto.getValor());

        if (contaPagante.getId() == contaRecebedor.getId())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Não é possível transferir para a mesma conta");
        if (valor.compareTo(contaPagante.getSaldo()) > 0)
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Saldo insuficiente na conta bancária pagante");

        BigDecimal novoSaldoPagante = contaPagante.getSaldo().subtract(valor);
        contaPagante.setSaldo(novoSaldoPagante);
        contaBancariaService.save(contaPagante);

        BigDecimal novoSaldoRecebedor = contaRecebedor.getSaldo().add(valor);
        contaRecebedor.setSaldo(novoSaldoRecebedor);
        contaBancariaService.save(contaRecebedor);

        return ResultadoTransferenciaDto.builder()
                .totalTransferido(valor.setScale(2, RoundingMode.HALF_EVEN))
                .novoSaldoPagante(novoSaldoPagante.setScale(2, RoundingMode.HALF_EVEN))
                .novoSaldoRecebedor(novoSaldoRecebedor.setScale(2, RoundingMode.HALF_EVEN))
                .build();
    }

}
