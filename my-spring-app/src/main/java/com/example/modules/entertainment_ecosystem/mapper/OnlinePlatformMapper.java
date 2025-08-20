package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.OnlinePlatform;
import com.example.modules.entertainment_ecosystem.dto.OnlinePlatformDto;
import com.example.modules.entertainment_ecosystem.dtosimple.OnlinePlatformSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface OnlinePlatformMapper {

    OnlinePlatformMapper INSTANCE = Mappers.getMapper(OnlinePlatformMapper.class);

    OnlinePlatformDto toDto(OnlinePlatform onlineplatform);

    OnlinePlatformSimpleDto toSimpleDto(OnlinePlatform onlineplatform);

    @InheritInverseConfiguration
    OnlinePlatform toEntity(OnlinePlatformDto onlineplatformDto);

    List<OnlinePlatformDto> toDtoList(List<OnlinePlatform> onlineplatformList);

    List<OnlinePlatform> toEntityList(List<OnlinePlatformDto> onlineplatformDtoList);

    void updateEntityFromDto(OnlinePlatformDto dto, @MappingTarget OnlinePlatform entity);

}