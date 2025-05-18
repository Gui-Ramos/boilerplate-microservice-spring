package com.microservice.boilerplate.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.boilerplate.dto.AlunoDTO;
import com.microservice.boilerplate.model.Aluno;
import com.microservice.boilerplate.repository.AlunoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AlunoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criarAluno_DeveRetornarStatusCreated_QuandoDadosValidos() throws Exception {
        AlunoDTO dto = new AlunoDTO();
        dto.setNome("João Silva");

        mockMvc.perform(post("/api/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nome", is("João Silva")));
    }

    @Test
    void buscarAluno_DeveRetornarAluno_QuandoExistir() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Maria Souza");
        aluno = repository.save(aluno);

        mockMvc.perform(get("/api/alunos/{id}", aluno.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(aluno.getId().toString())))
                .andExpect(jsonPath("$.nome", is("Maria Souza")));
    }

    @Test
    void listarAlunos_DeveRetornarListaDeAlunos() throws Exception {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Aluno 1");
        repository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Aluno 2");
        repository.save(aluno2);

        mockMvc.perform(get("/api/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Aluno 1")))
                .andExpect(jsonPath("$[1].nome", is("Aluno 2")));
    }

    @Test
    void atualizarAluno_DeveRetornarAlunoAtualizado() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Nome Antigo");
        aluno = repository.save(aluno);

        AlunoDTO dto = new AlunoDTO();
        dto.setNome("Nome Atualizado");

        mockMvc.perform(put("/api/alunos/{id}", aluno.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Nome Atualizado")));
    }

    @Test
    void deletarAluno_DeveRetornarNoContent() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno para deletar");
        aluno = repository.save(aluno);

        mockMvc.perform(delete("/api/alunos/{id}", aluno.getId())).andExpect(status().isNoContent());
    }
}
