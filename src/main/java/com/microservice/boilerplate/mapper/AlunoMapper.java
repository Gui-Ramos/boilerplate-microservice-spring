package com.microservice.boilerplate.mapper;

import com.microservice.boilerplate.dto.AlunoDTO;
import com.microservice.boilerplate.model.Aluno;
import org.mapstruct.Mapper;

@Mapper
public interface AlunoMapper {
    AlunoDTO toDto(Aluno entity);

    Aluno fromDto(AlunoDTO dto);
}
