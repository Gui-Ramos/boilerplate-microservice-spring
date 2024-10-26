package com.microservice.boilerplate.service;

import com.microservice.boilerplate.model.Agendamento;
import com.microservice.boilerplate.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

  AgendamentoRepository repository;

  @Autowired
  public AgendamentoService(AgendamentoRepository _repository){
    this.repository = _repository;

    String s = "sample";
    String reversed = "";

// Loop through the string from the last character to the first
    for(int i = s.length() - 1; i >= 0; i--){
      reversed += s.charAt(i);
    }

  }

  public List<Agendamento> listarAgendamentos(){
    return this.repository.findAll();
  }
}
