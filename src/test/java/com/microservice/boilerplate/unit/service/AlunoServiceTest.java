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
    @Test
    public void listarTodos_DeveRetornarListaVazia() {
        when(repository.findAll()).thenReturn(List.of());
        List<AlunoDTO> result = service.listarTodos();
        assertTrue(result.isEmpty());
    }

    @Test
    public void listarTodos_DeveRetornarAlunos() {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Teste");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setCep("01000-000");

        Aluno aluno = Aluno.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .cpf("123.456.789-00")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .telefone("11999999999")
                .endereco(endereco)
                .dataCadastro(LocalDateTime.now())
                .status(Status.ATIVO)
                .matricula("20230001")
                .genero(Genero.MASCULINO)
                .build();

        when(repository.findAll()).thenReturn(List.of(aluno));
        List<AlunoDTO> result = service.listarAlunos();

        assertFalse(result.isEmpty());
        AlunoDTO dto = result.get(0);
        assertEquals("João Silva", dto.getNome());
        assertEquals("Rua Teste", dto.getRua());
        assertEquals("123", dto.getNumero());
        assertEquals("São Paulo", dto.getCidade());
        assertEquals("SP", dto.getEstado());
    }

    @Test
    public void buscarPorId_DeveRetornarAlunoExistente() {
        UUID id = UUID.randomUUID();
        Aluno aluno = new Aluno();
        when(repository.findById(id)).thenReturn(Optional.of(aluno));

        AlunoDTO result = service.buscarPorId(id);
        assertNotNull(result);
    }

    @Test
    public void buscarPorId_DeveLancarExcecaoQuandoNaoExistir() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.buscarPorId(id));
    }

    @Test
    public void criar_DeveSalvarNovoAluno() {
        AlunoDTO dto = new AlunoDTO();
        Aluno alunoSalvo = new Aluno();
        when(repository.save(any(Aluno.class))).thenReturn(alunoSalvo);

        AlunoDTO result = service.criar(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(any(Aluno.class));
    }

    @Test
    public void deletar_DeveRemoverAlunoExistente() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);

        service.deletar(id);
        verify(repository, times(1)).deleteById(id);
    }
}
