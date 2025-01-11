package com.microservice.boilerplate.service;

import com.microservice.boilerplate.dto.AgendamentoDTO;
import com.microservice.boilerplate.mapper.AgendamentoMapper;
import com.microservice.boilerplate.repository.AgendamentoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    AgendamentoRepository repository;

    @Autowired
    public AgendamentoService(AgendamentoRepository _repository) {
        this.repository = _repository;
    }

    public List<AgendamentoDTO> listarAgendamentos() {
        return this.repository.findAll().stream()
                .map(AgendamentoMapper.INSTANCE::toDto)
                .toList();
    }
}
