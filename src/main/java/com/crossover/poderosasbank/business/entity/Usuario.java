package com.crossover.poderosasbank.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"contaBancaria"})
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String senha;

    // -----------

    @JsonIgnore
    @OneToOne(mappedBy = "usuario")
    private ContaBancaria contaBancaria;

}
