package com.microservice.boilerplate.mapper;

import com.microservice.boilerplate.dto.AgendamentoDTO;
import com.microservice.boilerplate.model.Agendamento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AgendamentoMapper {
    AgendamentoMapper INSTANCE = Mappers.getMapper(AgendamentoMapper.class);

    AgendamentoDTO toDto(Agendamento entity);

    Agendamento fromDto(AgendamentoDTO dto);
}
