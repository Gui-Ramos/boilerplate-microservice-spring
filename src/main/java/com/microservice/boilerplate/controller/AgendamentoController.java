package com.microservice.boilerplate.controller;

import com.microservice.boilerplate.model.Agendamento;
import com.microservice.boilerplate.service.AgendamentoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    AgendamentoService service;

    @Autowired
    public AgendamentoController(AgendamentoService _service) {
        this.service = _service;
    }

    @GetMapping("/")
    public ResponseEntity<List<Agendamento>> listAgendamentos() {
        return ResponseEntity.ok(service.listarAgendamentos());
    }
}
