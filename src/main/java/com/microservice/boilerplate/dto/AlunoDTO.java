package com.microservice.boilerplate.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class AlunoDTO extends AbstractDTO {
    private String nome;
    private String matricula;
    private String email;
}
