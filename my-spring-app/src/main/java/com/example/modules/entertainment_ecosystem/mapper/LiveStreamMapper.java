package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.LiveStream;
import com.example.modules.entertainment_ecosystem.dto.LiveStreamDto;
import com.example.modules.entertainment_ecosystem.dtosimple.LiveStreamSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface LiveStreamMapper {

    LiveStreamMapper INSTANCE = Mappers.getMapper(LiveStreamMapper.class);

    LiveStreamDto toDto(LiveStream livestream);

    LiveStreamSimpleDto toSimpleDto(LiveStream livestream);

    @InheritInverseConfiguration
    LiveStream toEntity(LiveStreamDto livestreamDto);

    List<LiveStreamDto> toDtoList(List<LiveStream> livestreamList);

    List<LiveStream> toEntityList(List<LiveStreamDto> livestreamDtoList);

    void updateEntityFromDto(LiveStreamDto dto, @MappingTarget LiveStream entity);

}