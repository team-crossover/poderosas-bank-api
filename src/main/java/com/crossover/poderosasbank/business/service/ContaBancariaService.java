package com.crossover.poderosasbank.business.service;

import com.crossover.poderosasbank.business.entity.ContaBancaria;
import com.crossover.poderosasbank.business.entity.Usuario;
import com.crossover.poderosasbank.business.enums.TipoConta;
import com.crossover.poderosasbank.data.repository.ContaBancariaRepository;
import com.crossover.poderosasbank.presentation.dto.ValidarContaBancariaDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
@Transactional
public class ContaBancariaService extends CrudService<ContaBancaria, Long, ContaBancariaRepository> {

    public ContaBancaria findByNumero(String numero) {
        return getRepository().findByNumero(numero).orElse(null);
    }

    public ContaBancaria generate(TipoConta tipoConta, Usuario usuario) {
        String numero = null;
        while (numero == null || findByNumero(numero) != null) {
            numero = String.format("%d%s", tipoConta.ordinal(), RandomStringUtils.randomNumeric(4));
        }

        String dv = RandomStringUtils.randomNumeric(1);
        String agencia = RandomStringUtils.randomNumeric(4);

        ContaBancaria conta = ContaBancaria.builder()
                .tipo(tipoConta)
                .numero(numero)
                .digito(dv)
                .agencia(agencia)
                .usuario(usuario)
                .build();
        return save(conta);
    }

    public boolean validate(ValidarContaBancariaDto validarContaBancariaDto) {
        String numero = String.format("%05d", Integer.parseInt(validarContaBancariaDto.getNumero()));
        ContaBancaria conta = findByNumero(numero);
        if (conta == null)
            return false;
        String digito = validarContaBancariaDto.getDigito();
        if (!conta.getDigito().equals(digito))
            return false;
        String agencia = String.format("%04d", Integer.parseInt(validarContaBancariaDto.getAgencia()));
        if (!conta.getAgencia().equals(agencia))
            return false;
        return true;
    }

}
