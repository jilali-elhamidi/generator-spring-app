package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.DocumentType;
import com.example.modules.project_management.dto.DocumentTypeDto;
import com.example.modules.project_management.dtosimple.DocumentTypeSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface DocumentTypeMapper {

    DocumentTypeMapper INSTANCE = Mappers.getMapper(DocumentTypeMapper.class);

    DocumentTypeDto toDto(DocumentType documenttype);

    DocumentTypeSimpleDto toSimpleDto(DocumentType documenttype);

    @InheritInverseConfiguration
    DocumentType toEntity(DocumentTypeDto documenttypeDto);

    List<DocumentTypeDto> toDtoList(List<DocumentType> documenttypeList);

    List<DocumentType> toEntityList(List<DocumentTypeDto> documenttypeDtoList);

    void updateEntityFromDto(DocumentTypeDto dto, @MappingTarget DocumentType entity);

}