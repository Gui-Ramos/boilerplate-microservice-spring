package com.microservice.boilerplate.dto;

import com.microservice.boilerplate.model.Genero;
import com.microservice.boilerplate.model.Status;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class AlunoDTO extends AbstractDTO {
    private String nome;
    private String email;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private LocalDateTime dataCadastro;
    private Status status;
    private String matricula;
    private Genero genero;
    private String observacoes;
}
