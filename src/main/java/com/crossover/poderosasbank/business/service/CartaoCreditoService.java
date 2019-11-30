package com.crossover.poderosasbank.business.service;

import com.crossover.poderosasbank.business.entity.CartaoCredito;
import com.crossover.poderosasbank.business.entity.ContaBancaria;
import com.crossover.poderosasbank.data.repository.CartaoCreditoRepository;
import com.crossover.poderosasbank.presentation.dto.DadosCartaoCreditoDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Transactional
public class CartaoCreditoService extends CrudService<CartaoCredito, Long, CartaoCreditoRepository> {

    public CartaoCredito findByNumero(String numero) {
        return getRepository().findByNumero(numero).orElse(null);
    }

    public CartaoCredito findByDadosAndValidate(DadosCartaoCreditoDto dadosCartaoCreditoDto) {
        String numero = String.format("%016d", Long.parseLong(dadosCartaoCreditoDto.getNumero()));
        CartaoCredito cartao = findByNumero(numero);
        if (cartao == null)
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Número de cartão não encontrado");
        if (!cartao.getValidade().equals(dadosCartaoCreditoDto.getValidade()))
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Validade do cartão incorreta");
        String cvv = String.format("%03d", Integer.parseInt(dadosCartaoCreditoDto.getCvv()));
        if (!cartao.getCvv().equals(cvv))
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "CVV do cartão incorreto");
        return cartao;
    }

    public CartaoCredito generate(ContaBancaria contaBancaria) {
        String numero = null;
        while (numero == null || findByNumero(numero) != null) {
            numero = RandomStringUtils.randomNumeric(16);
        }

        String cvv = RandomStringUtils.randomNumeric(3);
        String validade = String.format("%02d/20%02d", RandomUtils.nextInt(1, 28), RandomUtils.nextInt(25, 40));
        BigDecimal limite = BigDecimal.valueOf(RandomUtils.nextDouble(500.0, 10000.0));

        CartaoCredito cartaoCredito = CartaoCredito.builder()
                .numero(numero)
                .cvv(cvv)
                .validade(validade)
                .contaBancaria(contaBancaria)
                .limite(limite)
                .faturaAtual(BigDecimal.ZERO)
                .build();
        return save(cartaoCredito);
    }
}
