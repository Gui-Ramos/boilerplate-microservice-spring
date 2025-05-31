package com.microservice.boirplate.controller;

import com.microservice.boirplate.dto.MatriculaDTO;
import com.microservice.boirplate.service.MatriculaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatriculaController.class)
class MatriculaControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatriculaService service;

    @Test
    void listarTodas_deveRetornarOk() throws Exception {
        when(service.listarTodas()).thenReturn(List.of(new MatriculaDTO()));
        
        mockMvc.perform(get("/matricula"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void buscarPorId_deveRetornarMatricula() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.buscarPorId(id)).thenReturn(new MatriculaDTO());
        
        mockMvc.perform(get("/matricula/{id}", id))
               .andExpect(status().isOk());
    }

    @Test
    void criar_deveRetornarCreated() throws Exception {
        MatriculaDTO dto = new MatriculaDTO();
        dto.setStatus("ATIVA");
        dto.setDataMatricula(LocalDate.now());
        
        when(service.criar(any())).thenReturn(dto);
        
        mockMvc.perform(post("/matricula")
               .contentType(MediaType.APPLICATION_JSON)
               .content("""
                       {
                           "status": "ATIVA",
                           "dataMatricula": "2023-01-01"
                       }
                       """))
               .andExpect(status().isCreated());
    }

    @Test
    void atualizar_deveRetornarOk() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.atualizar(any(), any())).thenReturn(new MatriculaDTO());
        
        mockMvc.perform(put("/matricula/{id}", id)
               .contentType(MediaType.APPLICATION_JSON)
               .content("""
                       {
                           "status": "ATIVA"
                       }
                       """))
               .andExpect(status().isOk());
    }

    @Test
    void deletar_deveRetornarNoContent() throws Exception {
        UUID id = UUID.randomUUID();
        
        mockMvc.perform(delete("/matricula/{id}", id))
               .andExpect(status().isNoContent());
    }
}
