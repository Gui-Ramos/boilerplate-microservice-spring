package com.microservice.boilerplate.service;

import com.microservice.boilerplate.dto.AlunoDTO;
import com.microservice.boilerplate.mapper.AlunoMapper;
import com.microservice.boilerplate.repository.AlunoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {
    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public List<AlunoDTO> listarAlunos() {
        return repository.findAll().stream().map(AlunoMapper.INSTANCE::toDto).toList();
    }
}
