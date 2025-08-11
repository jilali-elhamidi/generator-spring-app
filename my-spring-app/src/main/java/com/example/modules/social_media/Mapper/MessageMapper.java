package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Message;
import com.example.modules.social_media.dto.MessageDto;
import com.example.modules.social_media.dto.MessageSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MessageMapper {

MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);


MessageDto toDto(Message message);

MessageSimpleDto toSimpleDto(Message message);

@InheritInverseConfiguration
Message toEntity(MessageDto messageDto);

List<MessageDto> toDtoList(List<Message> messageList);

    List<Message> toEntityList(List<MessageDto> messageDtoList);
        }