package com.microservice.boilerplate.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.microservice.boilerplate.dto.AlunoDTO;
import com.microservice.boilerplate.repository.AlunoRepository;
import com.microservice.boilerplate.service.AlunoService;
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
}
