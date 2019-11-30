package com.crossover.poderosasbank.presentation.controller;

import com.crossover.poderosasbank.business.service.UsuarioService;
import com.crossover.poderosasbank.presentation.dto.LoginDto;
import com.crossover.poderosasbank.presentation.dto.NovoUsuarioDto;
import com.crossover.poderosasbank.presentation.dto.results.UsuarioDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/api/v1/usuarios")
    @ApiOperation("Obter dados de todos usuários")
    private List<UsuarioDto> getAll() {
        return usuarioService.findAll().stream().map(UsuarioDto::fromUsuario).collect(Collectors.toList());
    }

    @PostMapping("/api/v1/usuario/login")
    @ApiOperation("Autenticar usuário")
    private UsuarioDto login(@RequestBody @Valid LoginDto loginDto) {
        return usuarioService.authenticate(loginDto);
    }

    @PostMapping("/api/v1/usuarios")
    @ApiOperation("Registrar novo usuário")
    private UsuarioDto register(@RequestBody @Valid NovoUsuarioDto novoUsuarioDto) {
        return usuarioService.register(novoUsuarioDto);
    }

}
