package com.microservice.boilerplate.service;

import com.microservice.boilerplate.dto.AlunoDTO;
import com.microservice.boilerplate.mapper.AlunoMapper;
import com.microservice.boilerplate.model.Aluno;
import com.microservice.boilerplate.model.Genero;
import com.microservice.boilerplate.model.Status;
import com.microservice.boilerplate.repository.AlunoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlunoService {
    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public List<AlunoDTO> listarTodos() {
        return repository.findAll().stream().map(AlunoMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    public AlunoDTO buscarPorId(UUID id) {
        return repository
                .findById(id)
                .map(AlunoMapper.INSTANCE::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
    }

    @Transactional
    public AlunoDTO criar(AlunoDTO alunoDTO) {
        Aluno aluno = AlunoMapper.INSTANCE.fromDto(alunoDTO);
        aluno = repository.save(aluno);
        return AlunoMapper.INSTANCE.toDto(aluno);
    }

    @Transactional
    public AlunoDTO atualizar(UUID id, AlunoDTO alunoDTO) {
        Aluno alunoExistente =
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));

        // TODO implementar patch update
        //        mapper.updateEntity(alunoDTO, alunoExistente);
        Aluno alunoAtualizado = repository.save(alunoExistente);
        return AlunoMapper.INSTANCE.toDto(alunoAtualizado);
    }

    @Transactional
    public void deletar(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Aluno não encontrado");
        }
        repository.deleteById(id);
    }

    @Transactional
    public AlunoDTO atualizarParcialmente(UUID id, Map<String, Object> campos) {
        if (campos == null || campos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum campo fornecido para atualização");
        }

        Aluno aluno = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));

        validarCampos(campos);

        // Validação dos campos
        campos.forEach((campo, valor) -> {
            if (valor == null) {
                throw new IllegalArgumentException("Valor não pode ser nulo para o campo: " + campo);
            }

            if (valor instanceof String) {
                String strValor = (String) valor;
                if (strValor.isBlank()) {
                    throw new IllegalArgumentException("Valor não pode ser vazio para o campo: " + campo);
                }

                // Validação adicional para campos específicos
                switch (campo) {
                    case "cpf" -> {
                        if (!strValor.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
                            throw new IllegalArgumentException("CPF deve estar no formato 999.999.999-99");
                        }
                    }
                    case "cep" -> {
                        if (!strValor.matches("\\d{5}-\\d{3}")) {
                            throw new IllegalArgumentException("CEP deve estar no formato 99999-999");
                        }
                    }
                }
            }
        });

        // Atualização dos campos
        campos.forEach((campo, valor) -> {
            switch (campo) {
                case "nome" -> aluno.setNome((String) valor);
                case "email" -> {
                    String email = (String) valor;
                    if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                        throw new IllegalArgumentException("Email inválido");
                    }
                    aluno.setEmail(email);
                }
                case "cpf" -> aluno.setCpf((String) valor);
                case "dataNascimento" -> {
                    try {
                        aluno.setDataNascimento(LocalDate.parse((String) valor));
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Data de nascimento inválida. Use o formato AAAA-MM-DD");
                    }
                }
                case "telefone" -> aluno.setTelefone((String) valor);
                case "rua" -> aluno.getEndereco().setRua((String) valor);
                case "numero" -> aluno.getEndereco().setNumero((String) valor);
                case "bairro" -> aluno.getEndereco().setBairro((String) valor);
                case "cidade" -> aluno.getEndereco().setCidade((String) valor);
                case "estado" -> aluno.getEndereco().setEstado((String) valor);
                case "cep" -> aluno.getEndereco().setCep((String) valor);
                case "status" -> aluno.setStatus(Status.valueOf((String) valor));
                case "matricula" -> aluno.setMatricula((String) valor);
                case "genero" -> aluno.setGenero(Genero.valueOf((String) valor));
                default -> throw new IllegalArgumentException("Campo inválido: " + campo);
            }
        });

        aluno.setDataAtualizacao(LocalDateTime.now());
        Aluno alunoAtualizado = repository.save(aluno);
        return AlunoMapper.INSTANCE.toDto(alunoAtualizado);
    }
}
