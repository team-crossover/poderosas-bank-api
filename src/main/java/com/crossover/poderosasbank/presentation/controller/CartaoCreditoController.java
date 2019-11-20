package com.crossover.poderosasbank.presentation.controller;

import com.crossover.poderosasbank.business.entity.CartaoCredito;
import com.crossover.poderosasbank.business.entity.ContaBancaria;
import com.crossover.poderosasbank.business.service.CartaoCreditoService;
import com.crossover.poderosasbank.business.service.ContaBancariaService;
import com.crossover.poderosasbank.presentation.dto.RespostaSimplesDto;
import com.crossover.poderosasbank.presentation.dto.ValidarCartaoCreditoDto;
import com.crossover.poderosasbank.presentation.dto.readonly.CartaoCreditoDto;
import com.crossover.poderosasbank.presentation.dto.readonly.ContaBancariaDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CartaoCreditoController {

    @Autowired
    private CartaoCreditoService cartaoCreditoService;

    @GetMapping("/api/v1/cartoes")
    @ApiOperation("Obtém dados de todos cartões de crédito")
    private List<CartaoCreditoDto> getAll() {
        return cartaoCreditoService.findAll().stream().map(CartaoCreditoDto::fromCartao).collect(Collectors.toList());
    }

    @PostMapping("/api/v1/cartoes/validar")
    @ApiOperation("Valida se os dados são de um cartão existente")
    private RespostaSimplesDto validar(@RequestBody @Valid ValidarCartaoCreditoDto validarCartaoCreditoDto) {
        if (cartaoCreditoService.validate(validarCartaoCreditoDto))
            return new RespostaSimplesDto(HttpStatus.OK, "/api/v1/cartoes/validar", "Valid");
        else
            return new RespostaSimplesDto(HttpStatus.OK, "/api/v1/cartoes/validar", "Invalid");
    }

    @GetMapping("/api/v1/cartao/{id}")
    @ApiOperation("Obtém dados de um cartão de crédito")
    private CartaoCreditoDto getById(@ApiParam("ID do cartão") @PathVariable(value = "id", required = true) Long id) {
        CartaoCredito cartao = cartaoCreditoService.findById(id);
        if (cartao == null)
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        return CartaoCreditoDto.fromCartao(cartao);
    }


}
