package com.crossover.poderosasbank.data.config;

import com.crossover.poderosasbank.business.entity.CartaoCredito;
import com.crossover.poderosasbank.business.entity.ContaBancaria;
import com.crossover.poderosasbank.business.entity.Usuario;
import com.crossover.poderosasbank.business.enums.TipoConta;
import com.crossover.poderosasbank.business.service.CartaoCreditoService;
import com.crossover.poderosasbank.business.service.ContaBancariaService;
import com.crossover.poderosasbank.business.service.UsuarioService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ContaBancariaService contaBancariaService;

    @Autowired
    private CartaoCreditoService cartaoCreditoService;

    private boolean lodadedInitialData = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (!lodadedInitialData)
            loadInitialData();
    }

    private void loadInitialData() {
        createInitialUsers();
        lodadedInitialData = true;
    }

    private Usuario getNewUsuario(String username) {
        return Usuario.builder()
                .username(username)
                .senha("123")
                .build();
    }

    private CartaoCredito getNewCartao(String numero) {
        return CartaoCredito.builder()
                .numero(numero)
                .validade("20/2020")
                .cvv("999")
                .limite(new BigDecimal(RandomUtils.nextDouble(500.0, 10000.0)))
                .faturaAtual(new BigDecimal(RandomUtils.nextDouble(0.0, 500.0)))
                .build();
    }

    private ContaBancaria getNewConta(String numero) {
        return ContaBancaria.builder()
                .tipo(TipoConta.CORRENTE)
                .numero(numero)
                .digito("5")
                .agencia("6789")
                .saldo(new BigDecimal(RandomUtils.nextDouble(0.0, 10000.0)))
                .build();
    }

    private void createInitialUsers() {
        usuarioService.register(getNewUsuario("conta"), getNewConta("01234"), getNewCartao("5555666677778888"));
        usuarioService.register(getNewUsuario("conta3"), getNewConta("01235"), getNewCartao("5555666677778889"));
        usuarioService.register(getNewUsuario("conta4"), getNewConta("01236"), getNewCartao("5555666677778810"));
        usuarioService.register(getNewUsuario("conta5"), getNewConta("01237"), getNewCartao("5555666677778811"));
    }

}
