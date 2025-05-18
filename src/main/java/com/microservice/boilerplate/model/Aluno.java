package com.microservice.boilerplate.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "aluno", schema = "public")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Aluno extends AbstractModel {
    private String nome;
    private String email;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;

    @Embedded
    private Endereco endereco;

    private LocalDateTime dataCadastro;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String matricula;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Column(length = 1000)
    private String observacoes;
}

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Endereco {
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}

enum Status {
    ATIVO,
    INATIVO
}

enum Genero {
    MASCULINO,
    FEMININO,
    OUTRO,
    NAO_INFORMADO
}
