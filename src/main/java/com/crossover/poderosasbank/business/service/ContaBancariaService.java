package com.crossover.poderosasbank.business.service;

import com.crossover.poderosasbank.business.entity.ContaBancaria;
import com.crossover.poderosasbank.business.entity.Usuario;
import com.crossover.poderosasbank.business.enums.TipoConta;
import com.crossover.poderosasbank.data.repository.ContaBancariaRepository;
import com.crossover.poderosasbank.presentation.dto.DadosContaBancariaDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
public class ContaBancariaService extends CrudService<ContaBancaria, Long, ContaBancariaRepository> {

    public ContaBancaria findByNumero(String numero) {
        return getRepository().findByNumero(numero).orElse(null);
    }

    public ContaBancaria findByDadosAndValidate(DadosContaBancariaDto dadosContaBancariaDto) {
        String numero = String.format("%05d", Integer.parseInt(dadosContaBancariaDto.getNumero()));
        ContaBancaria conta = findByNumero(numero);
        if (conta == null)
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Conta bancária não encontrada");
        String digito = dadosContaBancariaDto.getDigito();
        if (!conta.getDigito().equals(digito))
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Dígito inválido");
        String agencia = String.format("%04d", Integer.parseInt(dadosContaBancariaDto.getAgencia()));
        if (!conta.getAgencia().equals(agencia))
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Agência inválida");
        return conta;
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
                .saldo(BigDecimal.ZERO)
                .build();
        return save(conta);
    }

}
