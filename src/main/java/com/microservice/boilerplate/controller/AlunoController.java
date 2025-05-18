package com.microservice.boilerplate.controller;

import com.microservice.boilerplate.dto.AlunoDTO;
import com.microservice.boilerplate.service.AlunoService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @GetMapping
    public List<AlunoDTO> listarAlunos() {
        return alunoService.listarAlunos();
    }

    @GetMapping("/{id}")
    public AlunoDTO buscarAluno(@PathVariable UUID id) {
        return alunoService.buscarAluno(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoDTO criarAluno(@RequestBody AlunoDTO alunoDTO) {
        return alunoService.criarAluno(alunoDTO);
    }

    @PutMapping("/{id}")
    public AlunoDTO atualizarAluno(@PathVariable UUID id, @RequestBody AlunoDTO alunoDTO) {
        return alunoService.atualizarAluno(id, alunoDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAluno(@PathVariable UUID id) {
        alunoService.deletarAluno(id);
    }
}
