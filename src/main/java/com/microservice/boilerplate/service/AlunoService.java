package com.microservice.boilerplate.service;

import com.microservice.boilerplate.dto.AlunoDTO;
import com.microservice.boilerplate.mapper.AlunoMapper;
import com.microservice.boilerplate.model.Aluno;
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

        // Valida campos obrigatórios
        campos.forEach((campo, valor) -> {
            if (valor == null) {
                throw new IllegalArgumentException("Valor não pode ser nulo para o campo: " + campo);
            }
            if (valor instanceof String && ((String) valor).isBlank()) {
                throw new IllegalArgumentException("Valor não pode ser vazio para o campo: " + campo);
            }
        });

        // Atualiza os campos usando MapStruct
        AlunoMapper.INSTANCE.updateFieldsFromMap(aluno, campos);

        // Validações específicas para alguns campos
        if (campos.containsKey("email")
                && !campos.get("email").toString().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Email inválido");
        }
        if (campos.containsKey("cpf") && !campos.get("cpf").toString().matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new IllegalArgumentException("CPF deve estar no formato 999.999.999-99");
        }
        if (campos.containsKey("cep") && !campos.get("cep").toString().matches("\\d{5}-\\d{3}")) {
            throw new IllegalArgumentException("CEP deve estar no formato 99999-999");
        }
        if (campos.containsKey("dataNascimento")) {
            try {
                aluno.setDataNascimento(
                        LocalDate.parse(campos.get("dataNascimento").toString()));
            } catch (Exception e) {
                throw new IllegalArgumentException("Data de nascimento inválida. Use o formato AAAA-MM-DD");
            }
        }

        aluno.setDataAtualizacao(LocalDateTime.now());
        Aluno alunoAtualizado = repository.save(aluno);
        return AlunoMapper.INSTANCE.toDto(alunoAtualizado);
    }
}
