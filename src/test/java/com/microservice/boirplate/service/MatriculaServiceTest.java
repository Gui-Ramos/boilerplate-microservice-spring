package com.microservice.boirplate.service;

import com.microservice.boirplate.dto.MatriculaDTO;
import com.microservice.boirplate.model.Matricula;
import com.microservice.boirplate.repository.MatriculaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatriculaServiceTest {

    @Mock
    private MatriculaRepository repository;

    @Mock
    private MatriculaMapper mapper;

    @InjectMocks
    private MatriculaService service;

    @Test
    void listarTodas_deveRetornarListaDeMatriculas() {
        Matricula matricula = new Matricula();
        MatriculaDTO dto = new MatriculaDTO();
        
        when(repository.findAll()).thenReturn(List.of(matricula));
        when(mapper.toDto(matricula)).thenReturn(dto);
        
        List<MatriculaDTO> result = service.listarTodas();
        
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void buscarPorId_deveRetornarMatricula() {
        UUID id = UUID.randomUUID();
        Matricula matricula = new Matricula();
        MatriculaDTO dto = new MatriculaDTO();
        
        when(repository.findById(id)).thenReturn(Optional.of(matricula));
        when(mapper.toDto(matricula)).thenReturn(dto);
        
        MatriculaDTO result = service.buscarPorId(id);
        
        assertEquals(dto, result);
    }

    @Test
    void criar_deveSalvarMatricula() {
        MatriculaDTO dto = new MatriculaDTO();
        Matricula matricula = new Matricula();
        Matricula matriculaSalva = new Matricula();
        MatriculaDTO dtoSalvo = new MatriculaDTO();
        
        when(mapper.toEntity(dto)).thenReturn(matricula);
        when(repository.save(matricula)).thenReturn(matriculaSalva);
        when(mapper.toDto(matriculaSalva)).thenReturn(dtoSalvo);
        
        MatriculaDTO result = service.criar(dto);
        
        assertEquals(dtoSalvo, result);
        verify(repository).save(matricula);
    }

    @Test
    void atualizar_deveAtualizarMatricula() {
        UUID id = UUID.randomUUID();
        MatriculaDTO dto = new MatriculaDTO();
        Matricula matricula = new Matricula();
        Matricula matriculaSalva = new Matricula();
        MatriculaDTO dtoAtualizado = new MatriculaDTO();
        
        when(repository.findById(id)).thenReturn(Optional.of(matricula));
        when(repository.save(matricula)).thenReturn(matriculaSalva);
        when(mapper.toDto(matriculaSalva)).thenReturn(dtoAtualizado);
        
        MatriculaDTO result = service.atualizar(id, dto);
        
        assertEquals(dtoAtualizado, result);
        verify(mapper).updateFromDto(matricula, dto);
    }

    @Test
    void deletar_deveChamarDelete() {
        UUID id = UUID.randomUUID();
        service.deletar(id);
        verify(repository).deleteById(id);
    }
}
