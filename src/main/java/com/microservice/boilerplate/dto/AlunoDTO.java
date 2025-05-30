package com.microservice.boilerplate.dto;

import com.microservice.boilerplate.model.Genero;
import com.microservice.boilerplate.model.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class AlunoDTO extends AbstractDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @NotNull(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato 999.999.999-99")
    private String cpf;

    private LocalDate dataNascimento;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter apenas números com DDD")
    private String telefone;

    @Size(max = 100, message = "Rua deve ter no máximo 100 caracteres")
    private String rua;

    @Size(max = 10, message = "Número deve ter no máximo 10 caracteres")
    private String numero;

    @Size(max = 50, message = "Bairro deve ter no máximo 50 caracteres")
    private String bairro;

    @Size(max = 50, message = "Cidade deve ter no máximo 50 caracteres")
    private String cidade;

    @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres")
    private String estado;

    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato 99999-999")
    private String cep;
    private LocalDateTime dataCadastro;
    private Status status;
    private String matricula;
    private Genero genero;
    private String observacoes;
}
