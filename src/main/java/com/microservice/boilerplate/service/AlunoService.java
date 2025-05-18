package com.microservice.boilerplate.service;

import com.microservice.boilerplate.dto.AlunoDTO;
import com.microservice.boilerplate.mapper.AlunoMapper;
import com.microservice.boilerplate.repository.AlunoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AlunoService {
    private final AlunoRepository repository;
    private final AlunoMapper mapper;

    public AlunoService(AlunoRepository repository, AlunoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<AlunoDTO> listarTodos() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public AlunoDTO buscarPorId(UUID id) {
        return repository
                .findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
    }

    public AlunoDTO criar(AlunoDTO alunoDTO) {
        Aluno aluno = mapper.toEntity(alunoDTO);
        aluno = repository.save(aluno);
        return mapper.toDto(aluno);
    }

    public AlunoDTO atualizar(UUID id, AlunoDTO alunoDTO) {
        Aluno alunoExistente =
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));

        mapper.updateEntity(alunoDTO, alunoExistente);
        Aluno alunoAtualizado = repository.save(alunoExistente);
        return mapper.toDto(alunoAtualizado);
    }

    public void deletar(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Aluno não encontrado");
        }
        repository.deleteById(id);
    }
}
