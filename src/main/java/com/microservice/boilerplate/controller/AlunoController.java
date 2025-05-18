package com.microservice.boilerplate.controller;

import com.microservice.boilerplate.dto.AlunoDTO;
import com.microservice.boilerplate.service.AlunoService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aluno")
public class AlunoController {
    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping
    public List<AlunoDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AlunoDTO> criar(@RequestBody AlunoDTO alunoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(alunoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> atualizar(@PathVariable UUID id, @RequestBody AlunoDTO alunoDTO) {
        return ResponseEntity.ok(service.atualizar(id, alunoDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualiza parcialmente um aluno",
            description = "Permite atualizar campos específicos de um aluno")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso"),
                @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
                @ApiResponse(responseCode = "404", description = "Aluno não encontrado"),
                @ApiResponse(responseCode = "500", description = "Erro interno")
            })
    public ResponseEntity<?> atualizarParcialmente(
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Mapa de campos para atualização")
                    @RequestBody
                    Map<String, Object> campos,
            @Parameter(description = "ETag para controle de concorrência")
                    @RequestHeader(value = "If-Match", required = false)
                    String eTag) {
        try {
            if (campos == null || campos.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Nenhum campo fornecido para atualização"));
            }

            AlunoDTO alunoAtualizado = service.atualizarParcialmente(id, campos);

            return ResponseEntity.ok().eTag(eTag != null ? eTag : "").body(alunoAtualizado);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
