package com.crossover.poderosasbank.presentation.dto;

import com.crossover.poderosasbank.business.entity.Usuario;
import com.crossover.poderosasbank.business.enums.TipoConta;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NovoUsuarioDto {

    @NotBlank
    @ApiModelProperty(example = "nelson")
    private String username;

    @NotBlank
    @ApiModelProperty(example = "123")
    private String senha;

    @NotNull
    @ApiModelProperty(example = "CORRENTE")
    private TipoConta tipoConta;

    public Usuario toUsuario() {
        return Usuario.builder()
                .username(username)
                .senha(senha)
                .build();
    }

}
