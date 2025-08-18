package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Attachment;
import com.example.modules.project_management.dto.AttachmentDto;
import com.example.modules.project_management.dtosimple.AttachmentSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    AttachmentMapper INSTANCE = Mappers.getMapper(AttachmentMapper.class);

    AttachmentDto toDto(Attachment attachment);

    AttachmentSimpleDto toSimpleDto(Attachment attachment);

    @InheritInverseConfiguration
    Attachment toEntity(AttachmentDto attachmentDto);

    List<AttachmentDto> toDtoList(List<Attachment> attachmentList);

    List<Attachment> toEntityList(List<AttachmentDto> attachmentDtoList);

    void updateEntityFromDto(AttachmentDto dto, @MappingTarget Attachment entity);

}