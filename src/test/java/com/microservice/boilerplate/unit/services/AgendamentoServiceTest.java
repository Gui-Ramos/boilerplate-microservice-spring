package com.microservice.boilerplate.unit.services;

import com.microservice.boilerplate.model.Agendamento;
import com.microservice.boilerplate.repository.AgendamentoRepository;
import com.microservice.boilerplate.service.AgendamentoService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AgendamentoServiceTest {

  @Mock
  AgendamentoRepository repository;

  @InjectMocks
  AgendamentoService service;

  @BeforeEach
  public void init(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void listarAgendamentos_ListaVazia(){

    List<Agendamento> list = new ArrayList<>();

    when(repository.findAll()).thenReturn(list);

    var result = service.listarAgendamentos();


    assertEquals(list.size(), result.size());

  }

}
