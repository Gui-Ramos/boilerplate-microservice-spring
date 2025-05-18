package com.microservice.boilerplate.service;

import com.microservice.boilerplate.dto.AlunoDTO;
import com.microservice.boilerplate.mapper.AlunoMapper;
import com.microservice.boilerplate.repository.AlunoRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository repository;
    private final AlunoMapper mapper;

    @Transactional(readOnly = true)
    public List<AlunoDTO> listarAlunos() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AlunoDTO buscarAluno(UUID id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new RuntimeException("Aluno não encontrado")));
    }

    @Transactional
    public AlunoDTO criarAluno(AlunoDTO alunoDTO) {
        var aluno = mapper.toEntity(alunoDTO);
        return mapper.toDto(repository.save(aluno));
    }

    @Transactional
    public AlunoDTO atualizarAluno(UUID id, AlunoDTO alunoDTO) {
        var alunoExistente = repository.findById(id).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        alunoExistente = mapper.update(alunoDTO, alunoExistente);
        return mapper.toDto(repository.save(alunoExistente));
    }

    @Transactional
    public void deletarAluno(UUID id) {
        repository.deleteById(id);
    }
}
