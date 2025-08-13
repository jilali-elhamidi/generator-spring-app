package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MessageThread;
import com.example.modules.entertainment_ecosystem.dto.MessageThreadDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MessageThreadSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MessageThreadMapper {

    MessageThreadMapper INSTANCE = Mappers.getMapper(MessageThreadMapper.class);

    MessageThreadDto toDto(MessageThread messagethread);

    MessageThreadSimpleDto toSimpleDto(MessageThread messagethread);

    @InheritInverseConfiguration
    MessageThread toEntity(MessageThreadDto messagethreadDto);

    List<MessageThreadDto> toDtoList(List<MessageThread> messagethreadList);

    List<MessageThread> toEntityList(List<MessageThreadDto> messagethreadDtoList);

}