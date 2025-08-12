package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.dto.StreamingPlatformDto;
import com.example.modules.entertainment_ecosystem.dtosimple.StreamingPlatformSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface StreamingPlatformMapper {

    StreamingPlatformMapper INSTANCE = Mappers.getMapper(StreamingPlatformMapper.class);

    StreamingPlatformDto toDto(StreamingPlatform streamingplatform);

    StreamingPlatformSimpleDto toSimpleDto(StreamingPlatform streamingplatform);

    @InheritInverseConfiguration
    StreamingPlatform toEntity(StreamingPlatformDto streamingplatformDto);

    List<StreamingPlatformDto> toDtoList(List<StreamingPlatform> streamingplatformList);

    List<StreamingPlatform> toEntityList(List<StreamingPlatformDto> streamingplatformDtoList);

}