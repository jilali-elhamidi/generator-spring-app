package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserActivityLog;
import com.example.modules.entertainment_ecosystem.dto.UserActivityLogDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserActivityLogSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserActivityLogMapper {

    UserActivityLogMapper INSTANCE = Mappers.getMapper(UserActivityLogMapper.class);

    UserActivityLogDto toDto(UserActivityLog useractivitylog);

    UserActivityLogSimpleDto toSimpleDto(UserActivityLog useractivitylog);

    @InheritInverseConfiguration
    UserActivityLog toEntity(UserActivityLogDto useractivitylogDto);

    List<UserActivityLogDto> toDtoList(List<UserActivityLog> useractivitylogList);

    List<UserActivityLog> toEntityList(List<UserActivityLogDto> useractivitylogDtoList);

    void updateEntityFromDto(UserActivityLogDto dto, @MappingTarget UserActivityLog entity);

}