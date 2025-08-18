package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Document;
import com.example.modules.project_management.dto.DocumentDto;
import com.example.modules.project_management.dtosimple.DocumentSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface DocumentMapper {

    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    DocumentDto toDto(Document document);

    DocumentSimpleDto toSimpleDto(Document document);

    @InheritInverseConfiguration
    Document toEntity(DocumentDto documentDto);

    List<DocumentDto> toDtoList(List<Document> documentList);

    List<Document> toEntityList(List<DocumentDto> documentDtoList);

    void updateEntityFromDto(DocumentDto dto, @MappingTarget Document entity);

}