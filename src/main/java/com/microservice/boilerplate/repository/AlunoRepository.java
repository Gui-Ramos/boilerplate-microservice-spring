package com.microservice.boilerplate.repository;

import com.microservice.boilerplate.model.Aluno;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, UUID> {
    // Métodos adicionais podem ser adicionados aqui quando necessário
}
