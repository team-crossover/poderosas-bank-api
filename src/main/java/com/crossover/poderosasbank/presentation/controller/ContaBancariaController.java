package com.crossover.poderosasbank.presentation.controller;

import com.crossover.poderosasbank.business.entity.ContaBancaria;
import com.crossover.poderosasbank.business.service.ContaBancariaService;
import com.crossover.poderosasbank.presentation.dto.RespostaSimplesDto;
import com.crossover.poderosasbank.presentation.dto.ValidarCartaoCreditoDto;
import com.crossover.poderosasbank.presentation.dto.ValidarContaBancariaDto;
import com.crossover.poderosasbank.presentation.dto.readonly.ContaBancariaDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ContaBancariaController {

    @Autowired
    private ContaBancariaService contaService;

    @GetMapping("/api/v1/contas")
    @ApiOperation("Obtém dados de todas contas")
    private List<ContaBancariaDto> getAll() {
        return contaService.findAll().stream().map(ContaBancariaDto::fromConta).collect(Collectors.toList());
    }

    @PostMapping("/api/v1/contas/validar")
    @ApiOperation("Valida se os dados são de uma conta existente")
    private RespostaSimplesDto validar(@RequestBody @Valid ValidarContaBancariaDto validarContaBancariaDto) {
        if (contaService.validate(validarContaBancariaDto))
            return new RespostaSimplesDto(HttpStatus.OK, "/api/v1/contas/validar", "Valid");
        else
            return new RespostaSimplesDto(HttpStatus.OK, "/api/v1/contas/validar", "Invalid");
    }

    @GetMapping("/api/v1/conta/{id}")
    @ApiOperation("Obtém dados de uma conta")
    private ContaBancariaDto getById(@ApiParam("ID da conta") @PathVariable(value = "id", required = true) Long id) {
        ContaBancaria conta = contaService.findById(id);
        if (conta == null)
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        return ContaBancariaDto.fromConta(conta);
    }

}
