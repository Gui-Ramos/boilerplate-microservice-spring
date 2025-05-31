package com.microservice.boilerplate.integration;

import static org.junit.jupiter.api.Assertions.*;

import com.microservice.boilerplate.dto.AlunoDTO;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlunoControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAtualizarParcialmenteAluno() {
        // Criar aluno inicial
        AlunoDTO aluno = new AlunoDTO();
        aluno.setNome("Teste");
        aluno.setEmail("teste@email.com");
        aluno.setCpf("123.456.789-00");

        ResponseEntity<AlunoDTO> createResponse = restTemplate.postForEntity("/aluno", aluno, AlunoDTO.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

        UUID id = UUID.fromString(createResponse.getBody().getId());

        // Atualização parcial
        Map<String, Object> updates = Map.of(
                "nome", "Nome Atualizado",
                "email", "novo@email.com");

        ResponseEntity<AlunoDTO> response =
                restTemplate.exchange("/aluno/" + id, HttpMethod.PATCH, new HttpEntity<>(updates), AlunoDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Nome Atualizado", response.getBody().getNome());
        assertEquals("novo@email.com", response.getBody().getEmail());
        // Criar aluno inicial
        AlunoDTO aluno = new AlunoDTO();
        aluno.setNome("Teste");
        aluno.setEmail("teste@email.com");

        ResponseEntity<AlunoDTO> createResponse = restTemplate.postForEntity("/aluno", aluno, AlunoDTO.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

        UUID id = UUID.fromString(createResponse.getBody().getId());

        // Atualização parcial
        Map<String, Object> updates = Map.of("nome", "Nome Atualizado", "email", "novo@email.com");

        ResponseEntity<AlunoDTO> response =
                restTemplate.exchange("/aluno/" + id, HttpMethod.PATCH, new HttpEntity<>(updates), AlunoDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Nome Atualizado", response.getBody().getNome());
        assertEquals("novo@email.com", response.getBody().getEmail());

        // Teste com email inválido
        Map<String, Object> updatesInvalid = Map.of("email", "email-invalido");
        ResponseEntity<String> errorResponse =
                restTemplate.exchange("/aluno/" + id, HttpMethod.PATCH, new HttpEntity<>(updatesInvalid), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getStatusCode());
        assertTrue(errorResponse.getBody().contains("Email deve ser válido"));
    }
}
