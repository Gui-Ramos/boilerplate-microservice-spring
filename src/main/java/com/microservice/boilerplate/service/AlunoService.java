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

        // Atualiza os campos usando MapStruct
        AlunoMapper.INSTANCE.updateFieldsFromMap(aluno, campos);

        aluno.setDataAtualizacao(LocalDateTime.now());
        Aluno alunoAtualizado = repository.save(aluno);
        return AlunoMapper.INSTANCE.toDto(alunoAtualizado);
    }
}
