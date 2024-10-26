package com.microservice.boilerplate.repository;

import com.microservice.boilerplate.model.Agendamento;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {}
