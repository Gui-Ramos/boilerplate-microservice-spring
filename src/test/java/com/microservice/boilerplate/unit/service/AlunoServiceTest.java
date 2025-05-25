package com.microservice.boilerplate.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.microservice.boilerplate.dto.AlunoDTO;
import com.microservice.boilerplate.model.Aluno;
import com.microservice.boilerplate.model.Endereco;
import com.microservice.boilerplate.model.Genero;
import com.microservice.boilerplate.model.Status;
import com.microservice.boilerplate.repository.AlunoRepository;
import com.microservice.boilerplate.service.AlunoService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
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
        List<AlunoDTO> result = service.listarTodos();

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

    @Test
    public void atualizarParcialmente_DeveAtualizarCamposInformados() {
        UUID id = UUID.randomUUID();
        Aluno alunoExistente = Aluno.builder()
                .nome("João Original")
                .email("original@email.com")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(alunoExistente));
        when(repository.save(any(Aluno.class))).thenReturn(alunoExistente);

        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setNome("João Atualizado");
        alunoDTO.setEmail("novo@email.com");

        service.atualizarParcialmente(id, alunoDTO);

        assertEquals("João Atualizado", alunoExistente.getNome());
        assertEquals("novo@email.com", alunoExistente.getEmail());
    }

    @Test
    public void atualizarParcialmente_DeveLancarExcecaoQuandoAlunoNaoExistir() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        AlunoDTO dto = new AlunoDTO();
        assertThrows(EntityNotFoundException.class, () -> service.atualizarParcialmente(id, dto));
    }

    @Test
    public void atualizarParcialmente_DeveAtualizarEndereco() {
        UUID id = UUID.randomUUID();
        Aluno alunoExistente = Aluno.builder().endereco(new Endereco()).build();

        when(repository.findById(id)).thenReturn(Optional.of(alunoExistente));
        when(repository.save(any(Aluno.class))).thenReturn(alunoExistente);

        Map<String, Object> campos = Map.of(
                "rua", "Nova Rua",
                "numero", "456");

        service.atualizarParcialmente(id, campos);

        assertEquals("Nova Rua", alunoExistente.getEndereco().getRua());
        assertEquals("456", alunoExistente.getEndereco().getNumero());
    }

    @Test
    public void atualizarParcialmente_DeveLancarExcecaoQuandoCampoInvalido() {
        UUID id = UUID.randomUUID();
        Aluno alunoExistente = Aluno.builder().build();
        when(repository.findById(id)).thenReturn(Optional.of(alunoExistente));

        Map<String, Object> campos = Map.of("campoInvalido", "valor");

        assertThrows(IllegalArgumentException.class, () -> service.atualizarParcialmente(id, campos));
    }

    @Test
    public void atualizarParcialmente_DeveLancarExcecaoQuandoCamposVazios() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> service.atualizarParcialmente(id, Map.of()));
    }

    @Test
    public void atualizarParcialmente_DeveLancarExcecaoQuandoEmailInvalido() {
        UUID id = UUID.randomUUID();
        Aluno alunoExistente = Aluno.builder().build();
        when(repository.findById(id)).thenReturn(Optional.of(alunoExistente));

        Map<String, Object> campos = Map.of("email", "email-invalido");

        assertThrows(IllegalArgumentException.class, () -> service.atualizarParcialmente(id, campos), "Email inválido");
    }

    @Test
    public void atualizarParcialmente_DeveLancarExcecaoQuandoDataNascimentoInvalida() {
        UUID id = UUID.randomUUID();
        Aluno alunoExistente = Aluno.builder().build();
        when(repository.findById(id)).thenReturn(Optional.of(alunoExistente));

        Map<String, Object> campos = Map.of("dataNascimento", "2023/01/01");

        assertThrows(
                IllegalArgumentException.class,
                () -> service.atualizarParcialmente(id, campos),
                "Data de nascimento inválida");
    }

    @Test
    public void atualizarParcialmente_DeveLancarExcecaoQuandoValorNulo() {
        UUID id = UUID.randomUUID();
        Aluno alunoExistente = Aluno.builder().build();
        when(repository.findById(id)).thenReturn(Optional.of(alunoExistente));

        Map<String, Object> campos = Map.of("nome", null);

        assertThrows(IllegalArgumentException.class, () -> service.atualizarParcialmente(id, campos));
    }

    @Test
    public void atualizarParcialmente_DeveValidarFormatoCPF() {
        UUID id = UUID.randomUUID();
        Aluno aluno = Aluno.builder().build();
        when(repository.findById(id)).thenReturn(Optional.of(aluno));

        Map<String, Object> camposInvalidos = Map.of("cpf", "12345678900"); // CPF sem formatação
        assertThrows(IllegalArgumentException.class, () -> service.atualizarParcialmente(id, camposInvalidos));
    }

    @Test
    public void atualizarParcialmente_DeveAtualizarDataAtualizacao() {
        UUID id = UUID.randomUUID();
        Aluno alunoExistente = Aluno.builder().build();
        when(repository.findById(id)).thenReturn(Optional.of(alunoExistente));
        when(repository.save(any(Aluno.class))).thenReturn(alunoExistente);

        service.atualizarParcialmente(id, Map.of("nome", "Novo Nome"));

        assertNotNull(alunoExistente.getDataAtualizacao());
    }
}
