package com.microservice.boilerplate.controller;

import com.microservice.boilerplate.dto.AlunoDTO;
import com.microservice.boilerplate.service.AlunoService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aluno")
public class AlunoController {
    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<AlunoDTO> listAlunos() {
        return service.listarAlunos();
    }
}
