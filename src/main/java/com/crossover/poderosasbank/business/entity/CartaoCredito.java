package com.crossover.poderosasbank.business.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {})
@Entity
@Table(name = "cartoes")
public class CartaoCredito {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Pattern(regexp = "^\\d*$")
    @Size(min = 16, max = 16)
    @Column(unique = true)
    private String numero;

    @NotBlank
    @Pattern(regexp = "^\\d\\d/\\d\\d\\d\\d$")
    private String validade;

    @NotBlank
    @Pattern(regexp = "^\\d\\d\\d$")
    private String cvv;

    @NotNull
    @Min(0)
    private BigDecimal limite;

    @NotNull
    private BigDecimal faturaAtual;

    // ----------

    @NotNull
    @ManyToOne
    private ContaBancaria contaBancaria;

}
