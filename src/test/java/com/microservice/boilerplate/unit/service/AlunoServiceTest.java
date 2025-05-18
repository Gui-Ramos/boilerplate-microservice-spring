package com.microservice.boilerplate.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.microservice.boilerplate.dto.AlunoDTO;
import com.microservice.boilerplate.model.Aluno;
import com.microservice.boilerplate.model.Genero;
import com.microservice.boilerplate.model.Status;
import com.microservice.boilerplate.repository.AlunoRepository;
import com.microservice.boilerplate.service.AlunoService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AlunoServiceTest {

    @Mock
    private AlunoRepository repository;

    @InjectMocks
    private AlunoService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void listarAlunos_ListaVazia() {
        when(repository.findAll()).thenReturn(List.of());
        List<AlunoDTO> result = service.listarAlunos();
        assertTrue(result.isEmpty());
    }

    @Test
    public void listarAlunos_ComDados() {
        Aluno aluno = Aluno.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .cpf("123.456.789-00")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .telefone("11999999999")
                .dataCadastro(LocalDateTime.now())
                .status(Status.ATIVO)
                .matricula("20230001")
                .genero(Genero.MASCULINO)
                .build();

        when(repository.findAll()).thenReturn(List.of(aluno));
        List<AlunoDTO> result = service.listarAlunos();
        assertFalse(result.isEmpty());
        assertEquals("João Silva", result.get(0).getNome());
    }
}
