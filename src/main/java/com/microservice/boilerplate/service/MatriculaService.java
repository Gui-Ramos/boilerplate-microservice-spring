package com.microservice.boilerplate.service;

import com.microservice.boilerplate.dto.MatriculaDTO;
import com.microservice.boilerplate.model.Matricula;
import com.microservice.boilerplate.repository.MatriculaRepository;
import com.microservice.boilerplate.mapper.MatriculaMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class MatriculaService {

    private final MatriculaRepository repository;
    private final MatriculaMapper mapper;

    public MatriculaService(MatriculaRepository repository, MatriculaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<MatriculaDTO> listarTodas() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public MatriculaDTO buscarPorId(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Matricula não encontrada com id: " + id));
    }

    public MatriculaDTO criar(MatriculaDTO dto) {
        Matricula matricula = mapper.toEntity(dto);
        Matricula salva = repository.save(matricula);
        return mapper.toDto(salva);
    }

    public MatriculaDTO atualizar(UUID id, MatriculaDTO dto) {
        Matricula matricula = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matricula não encontrada com id: " + id));
        mapper.updateFromDto(matricula, dto);
        Matricula atualizada = repository.save(matricula);
        return mapper.toDto(atualizada);
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }
}
package com.microservice.boilerplate.service;

import com.microservice.boilerplate.dto.MatriculaDTO;
import com.microservice.boilerplate.model.Matricula;
import com.microservice.boilerplate.repository.MatriculaRepository;
import com.microservice.boilerplate.mapper.MatriculaMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class MatriculaService {
    private final MatriculaRepository repository;
    private final MatriculaMapper mapper;

    public MatriculaService(MatriculaRepository repository, MatriculaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<MatriculaDTO> listarTodas() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public MatriculaDTO buscarPorId(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Matrícula não encontrada com id: " + id));
    }

    public MatriculaDTO criar(MatriculaDTO dto) {
        Matricula matricula = mapper.toEntity(dto);
        Matricula salva = repository.save(matricula);
        return mapper.toDto(salva);
    }

    public MatriculaDTO atualizar(UUID id, MatriculaDTO dto) {
        Matricula matricula = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matrícula não encontrada com id: " + id));
        mapper.updateFromDto(matricula, dto);
        Matricula atualizada = repository.save(matricula);
        return mapper.toDto(atualizada);
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }
}
