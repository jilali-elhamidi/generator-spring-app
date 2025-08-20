package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserConnection;
import com.example.modules.entertainment_ecosystem.dto.UserConnectionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserConnectionSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserConnectionMapper {

    UserConnectionMapper INSTANCE = Mappers.getMapper(UserConnectionMapper.class);

    UserConnectionDto toDto(UserConnection userconnection);

    UserConnectionSimpleDto toSimpleDto(UserConnection userconnection);

    @InheritInverseConfiguration
    UserConnection toEntity(UserConnectionDto userconnectionDto);

    List<UserConnectionDto> toDtoList(List<UserConnection> userconnectionList);

    List<UserConnection> toEntityList(List<UserConnectionDto> userconnectionDtoList);

    void updateEntityFromDto(UserConnectionDto dto, @MappingTarget UserConnection entity);

}