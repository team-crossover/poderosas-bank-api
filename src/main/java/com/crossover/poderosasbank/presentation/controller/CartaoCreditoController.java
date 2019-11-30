package com.crossover.poderosasbank.presentation.controller;

import com.crossover.poderosasbank.business.entity.CartaoCredito;
import com.crossover.poderosasbank.business.service.CartaoCreditoService;
import com.crossover.poderosasbank.presentation.dto.DadosCartaoCreditoDto;
import com.crossover.poderosasbank.presentation.dto.results.CartaoCreditoDto;
import com.crossover.poderosasbank.presentation.dto.results.ResultadoValidacaoDto;
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
    @ApiOperation("Valida se os dados são de um cartão existente, se sim, retorna o cartão")
    private CartaoCreditoDto validar(@RequestBody @Valid DadosCartaoCreditoDto dadosCartaoCreditoDto) {
        return CartaoCreditoDto.fromCartao(cartaoCreditoService.findByDadosAndValidate(dadosCartaoCreditoDto));
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
