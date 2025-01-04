package com.microservice.boilerplate.service;

import com.microservice.boilerplate.model.Agendamento;
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

    public List<Agendamento> listarAgendamentos() {
        return this.repository.findAll();
    }
}
