package com.microservice.boilerplate.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.microservice.boilerplate.dto.AgendamentoDTO;
import com.microservice.boilerplate.extensions.LoggingTestExtension;
import com.microservice.boilerplate.model.Agendamento;
import com.microservice.boilerplate.repository.AgendamentoRepository;
import com.microservice.boilerplate.service.AgendamentoService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(LoggingTestExtension.class)
public class AgendamentoServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(AgendamentoServiceTest.class);

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

        List<AgendamentoDTO> result = service.listarAgendamentos();

        assertEquals(0, result.size());
    }
}
