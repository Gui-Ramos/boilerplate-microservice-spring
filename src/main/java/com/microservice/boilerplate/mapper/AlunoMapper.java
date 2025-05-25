package com.microservice.boilerplate.mapper;

import com.microservice.boilerplate.dto.AlunoDTO;
import com.microservice.boilerplate.model.Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AlunoMapper {
    AlunoMapper INSTANCE = Mappers.getMapper(AlunoMapper.class);

    @Mapping(target = "rua", source = "endereco.rua")
    @Mapping(target = "numero", source = "endereco.numero")
    @Mapping(target = "bairro", source = "endereco.bairro")
    @Mapping(target = "cidade", source = "endereco.cidade")
    @Mapping(target = "estado", source = "endereco.estado")
    @Mapping(target = "cep", source = "endereco.cep")
    AlunoDTO toDto(Aluno entity);

    @Mapping(target = "endereco.rua", source = "rua")
    @Mapping(target = "endereco.numero", source = "numero")
    @Mapping(target = "endereco.bairro", source = "bairro")
    @Mapping(target = "endereco.cidade", source = "cidade")
    @Mapping(target = "endereco.estado", source = "estado")
    @Mapping(target = "endereco.cep", source = "cep")
    Aluno fromDto(AlunoDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    void updateFromDto(@MappingTarget Aluno entity, AlunoDTO dto);
}
