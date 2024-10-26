package com.microservice.boilerplate.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.microservice.boilerplate.model.Agendamento;
import com.microservice.boilerplate.repository.AgendamentoRepository;
import com.microservice.boilerplate.service.AgendamentoService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AgendamentoServiceTest {

    @Mock
    AgendamentoRepository repository;

    @InjectMocks
    AgendamentoService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void listarAgendamentos_ListaVazia() {

        List<Agendamento> list = new ArrayList<>();

        when(repository.findAll()).thenReturn(list);

        var result = service.listarAgendamentos();

        assertEquals(list.size(), result.size());
    }
}
