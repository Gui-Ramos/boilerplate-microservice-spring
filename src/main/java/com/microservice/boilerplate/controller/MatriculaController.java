package com.microservice.boilerplate.controller;

import com.microservice.boilerplate.dto.MatriculaDTO;
import com.microservice.boilerplate.service.MatriculaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/matricula")
public class MatriculaController {

    private final MatriculaService service;

    public MatriculaController(MatriculaService service) {
        this.service = service;
    }

    @GetMapping
    public List<MatriculaDTO> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public MatriculaDTO buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatriculaDTO criar(@RequestBody MatriculaDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public MatriculaDTO atualizar(@PathVariable UUID id, @RequestBody MatriculaDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable UUID id) {
        service.deletar(id);
    }
}
package com.microservice.boilerplate.controller;

import com.microservice.boilerplate.dto.MatriculaDTO;
import com.microservice.boilerplate.service.MatriculaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/matricula")
public class MatriculaController {
    private final MatriculaService service;

    public MatriculaController(MatriculaService service) {
        this.service = service;
    }

    @GetMapping
    public List<MatriculaDTO> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public MatriculaDTO buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatriculaDTO criar(@RequestBody MatriculaDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public MatriculaDTO atualizar(@PathVariable UUID id, @RequestBody MatriculaDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTent)
    public void deletar(@PathVariable UUID id) {
        service.deletar(id);
    }
}
