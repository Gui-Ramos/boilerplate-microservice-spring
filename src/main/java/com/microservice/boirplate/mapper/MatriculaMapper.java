package com.microservice.boirplate.mapper;

import com.microservice.boirplate.dto.MatriculaDTO;
import com.microservice.boirplate.model.Matricula;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MatriculaMapper {
    MatriculaMapper INSTANCE = Mappers.getMapper(MatriculaMapper.class);

    MatriculaDTO toDto(Matricula entity);

    Matricula toEntity(MatriculaDTO dto);

    void updateFromDto(@MappingTarget Matricula entity, MatriculaDTO dto);
}
