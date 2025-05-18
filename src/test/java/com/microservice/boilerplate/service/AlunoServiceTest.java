package com.microservice.boilerplate.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.microservice.boilerplate.dto.AlunoDTO;
import com.microservice.boilerplate.mapper.AlunoMapper;
import com.microservice.boilerplate.model.Aluno;
import com.microservice.boilerplate.repository.AlunoRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @Mock
    private AlunoRepository repository;

    @Mock
    private AlunoMapper mapper;

    @InjectMocks
    private AlunoService service;

    @Test
    void listarAlunos_DeveRetornarListaDeAlunos() {
        // Arrange
        Aluno aluno = mock(Aluno.class);
        AlunoDTO alunoDTO = mock(AlunoDTO.class);
        when(repository.findAll()).thenReturn(List.of(aluno));
        when(mapper.toDto(aluno)).thenReturn(alunoDTO);

        // Act
        List<AlunoDTO> resultado = service.listarAlunos();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals(alunoDTO, resultado.get(0));
    }

    @Test
    void buscarAluno_QuandoExistir_DeveRetornarAlunoDTO() {
        // Arrange
        UUID id = UUID.randomUUID();
        Aluno aluno = mock(Aluno.class);
        AlunoDTO alunoDTO = mock(AlunoDTO.class);
        when(repository.findById(id)).thenReturn(Optional.of(aluno));
        when(mapper.toDto(aluno)).thenReturn(alunoDTO);

        // Act
        AlunoDTO resultado = service.buscarAluno(id);

        // Assert
        assertEquals(alunoDTO, resultado);
    }

    @Test
    void buscarAluno_QuandoNaoExistir_DeveLancarExcecao() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.buscarAluno(id));
    }

    @Test
    void criarAluno_DeveSalvarERetornarAlunoDTO() {
        // Arrange
        AlunoDTO dto = mock(AlunoDTO.class);
        Aluno aluno = mock(Aluno.class);
        when(mapper.toEntity(dto)).thenReturn(aluno);
        when(mapper.toDto(aluno)).thenReturn(dto);
        when(repository.save(aluno)).thenReturn(aluno);

        // Act
        AlunoDTO resultado = service.criarAluno(dto);

        // Assert
        verify(repository).save(aluno);
        assertEquals(dto, resultado);
    }

    @Test
    void atualizarAluno_QuandoExistir_DeveAtualizarERetornarAlunoDTO() {
        // Arrange
        UUID id = UUID.randomUUID();
        AlunoDTO dto = mock(AlunoDTO.class);
        Aluno alunoExistente = mock(Aluno.class);
        Aluno alunoAtualizado = mock(Aluno.class);
        when(repository.findById(id)).thenReturn(Optional.of(alunoExistente));
        when(mapper.update(dto, alunoExistente)).thenReturn(alunoAtualizado);
        when(mapper.toDto(alunoAtualizado)).thenReturn(dto);
        when(repository.save(alunoAtualizado)).thenReturn(alunoAtualizado);

        // Act
        AlunoDTO resultado = service.atualizarAluno(id, dto);

        // Assert
        verify(repository).save(alunoAtualizado);
        assertEquals(dto, resultado);
    }

    @Test
    void deletarAluno_DeveChamarRepository() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        service.deletarAluno(id);

        // Assert
        verify(repository).deleteById(id);
    }
}
