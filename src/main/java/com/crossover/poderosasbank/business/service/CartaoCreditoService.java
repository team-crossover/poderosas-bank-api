package com.crossover.poderosasbank.business.service;

import com.crossover.poderosasbank.business.entity.CartaoCredito;
import com.crossover.poderosasbank.business.entity.ContaBancaria;
import com.crossover.poderosasbank.business.entity.Usuario;
import com.crossover.poderosasbank.business.enums.TipoConta;
import com.crossover.poderosasbank.data.repository.CartaoCreditoRepository;
import com.crossover.poderosasbank.presentation.dto.ValidarCartaoCreditoDto;
import com.crossover.poderosasbank.presentation.dto.ValidarContaBancariaDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
@Transactional
public class CartaoCreditoService extends CrudService<CartaoCredito, Long, CartaoCreditoRepository> {

    public CartaoCredito findByNumero(String numero) {
        return getRepository().findByNumero(numero).orElse(null);
    }

    public CartaoCredito generate(ContaBancaria contaBancaria) {
        String numero = null;
        while (numero == null || findByNumero(numero) != null) {
            numero = RandomStringUtils.randomNumeric(16);
        }

        String cvv = RandomStringUtils.randomNumeric(3);
        String validade = String.format("%02d/20%02d", RandomUtils.nextInt(1, 28), RandomUtils.nextInt(25, 40));

        CartaoCredito cartaoCredito = CartaoCredito.builder()
                .numero(numero)
                .cvv(cvv)
                .validade(validade)
                .contaBancaria(contaBancaria)
                .build();
        return save(cartaoCredito);
    }

    public boolean validate(ValidarCartaoCreditoDto validarCartaoCreditoDto) {
        String numero = String.format("%016d", Long.parseLong(validarCartaoCreditoDto.getNumero()));
        CartaoCredito cartao = findByNumero(numero);
        if (cartao == null)
            return false;
        if (!cartao.getValidade().equals(validarCartaoCreditoDto.getValidade()))
            return false;
        String cvv = String.format("%03d", Integer.parseInt(validarCartaoCreditoDto.getCvv()));
        if (!cartao.getCvv().equals(cvv))
            return false;
        return true;
    }
}
