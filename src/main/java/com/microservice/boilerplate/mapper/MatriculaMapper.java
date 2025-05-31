package com.microservice.boilerplate.mapper;

import com.microservice.boilerplate.dto.MatriculaDTO;
import com.microservice.boilerplate.model.Matricula;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MatriculaMapper {
    MatriculaDTO toDto(Matricula entity);
    Matricula toEntity(MatriculaDTO dto);
    void updateFromDto(@MappingTarget Matricula entity, MatriculaDTO dto);
}
