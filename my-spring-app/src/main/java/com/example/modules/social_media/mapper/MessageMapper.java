package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Message;
import com.example.modules.social_media.dto.MessageDto;
import com.example.modules.social_media.dto.MessageSimpleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class})
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    // Mappage pour le DTO riche
    MessageDto toDto(Message message);

    // Mappage pour le DTO simple
    MessageSimpleDto toSimpleDto(Message message);

    Message toEntity(MessageDto messageDto);

    List<MessageDto> toDtoList(List<Message> messageList);

    List<Message> toEntityList(List<MessageDto> messageDtoList);
}