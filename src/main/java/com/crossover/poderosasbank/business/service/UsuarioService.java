package com.crossover.poderosasbank.business.service;

import com.crossover.poderosasbank.business.entity.CartaoCredito;
import com.crossover.poderosasbank.business.entity.ContaBancaria;
import com.crossover.poderosasbank.business.entity.Usuario;
import com.crossover.poderosasbank.business.enums.TipoConta;
import com.crossover.poderosasbank.data.repository.UsuarioRepository;
import com.crossover.poderosasbank.presentation.dto.LoginDto;
import com.crossover.poderosasbank.presentation.dto.NovoUsuarioDto;
import com.crossover.poderosasbank.presentation.dto.results.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Transactional
public class UsuarioService extends CrudService<Usuario, Long, UsuarioRepository> {

    @Autowired
    private ContaBancariaService contaService;

    @Autowired
    private CartaoCreditoService cartaoCreditoService;

    public Usuario findByUsername(String username) {
        return getRepository().findByUsername(username).orElse(null);
    }

    public UsuarioDto authenticate(LoginDto loginDto) {
        Usuario usuario = findByUsername(loginDto.getUsername());
        if (usuario == null || !usuario.getSenha().equals(loginDto.getSenha())) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
        return UsuarioDto.fromUsuario(usuario);
    }

    public UsuarioDto register(NovoUsuarioDto novoUsuarioDto) {
        if (findByUsername(novoUsuarioDto.getUsername()) != null)
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Username já existe");

        Usuario usuario = save(novoUsuarioDto.toUsuario());
        ContaBancaria contaBancaria = contaService.generate(novoUsuarioDto.getTipoConta(), usuario);
        if (novoUsuarioDto.getTipoConta() == TipoConta.CORRENTE) {
            CartaoCredito cartaoCredito = cartaoCreditoService.generate(contaBancaria);
            contaBancaria.setCartoesCredito(Collections.singletonList(cartaoCredito));
        }
        usuario.setContaBancaria(contaBancaria);
        return UsuarioDto.fromUsuario(usuario);
    }

    public UsuarioDto register(Usuario usuario, ContaBancaria contaBancaria, CartaoCredito cartaoCredito) {
        if (findByUsername(usuario.getUsername()) != null)
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Username já existe");

        usuario = save(usuario);

        contaBancaria.setUsuario(usuario);
        contaBancaria = contaService.save(contaBancaria);

        if (contaBancaria.getTipo() == TipoConta.CORRENTE) {
            cartaoCredito.setContaBancaria(contaBancaria);
            cartaoCredito = cartaoCreditoService.save(cartaoCredito);
        }

        usuario = findByUsername(usuario.getUsername());
        return UsuarioDto.fromUsuario(usuario);
    }
}
