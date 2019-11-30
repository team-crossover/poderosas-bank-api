package com.crossover.poderosasbank.presentation.dto.results;

import com.crossover.poderosasbank.business.entity.Usuario;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private Long id;

    private Long idContaBancaria;

    @ApiModelProperty(example = "nelson")
    private String username;

    public static UsuarioDto fromUsuario(Usuario usuario) {
        return UsuarioDto.builder()
                .id(usuario.getId())
                .idContaBancaria(usuario.getContaBancaria() == null ? null : usuario.getContaBancaria().getId())
                .username(usuario.getUsername())
                .build();
    }

}
