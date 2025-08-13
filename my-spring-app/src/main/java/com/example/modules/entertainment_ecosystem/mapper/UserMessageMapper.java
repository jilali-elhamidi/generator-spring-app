package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserMessage;
import com.example.modules.entertainment_ecosystem.dto.UserMessageDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserMessageSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMessageMapper {

    UserMessageMapper INSTANCE = Mappers.getMapper(UserMessageMapper.class);

    UserMessageDto toDto(UserMessage usermessage);

    UserMessageSimpleDto toSimpleDto(UserMessage usermessage);

    @InheritInverseConfiguration
    UserMessage toEntity(UserMessageDto usermessageDto);

    List<UserMessageDto> toDtoList(List<UserMessage> usermessageList);

    List<UserMessage> toEntityList(List<UserMessageDto> usermessageDtoList);

}