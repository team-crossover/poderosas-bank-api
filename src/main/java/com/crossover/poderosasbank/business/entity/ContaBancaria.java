package com.crossover.poderosasbank.business.entity;

import com.crossover.poderosasbank.business.enums.TipoConta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"cartoesCredito"})
@Entity
@Table(name = "contas")
public class ContaBancaria {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private TipoConta tipo;

    @NotBlank
    @Pattern(regexp = "^\\d+$")
    private String numero;

    @NotBlank
    @Pattern(regexp = "^\\d$")
    private String digito;

    @NotBlank
    @Pattern(regexp = "^\\d+$")
    private String agencia;

    // -----------

    @OneToOne
    private Usuario usuario;

    // -----------

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "contaBancaria")
    private List<CartaoCredito> cartoesCredito = new ArrayList<>();


}
