package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.LiveStreamViewer;
import com.example.modules.entertainment_ecosystem.dto.LiveStreamViewerDto;
import com.example.modules.entertainment_ecosystem.dtosimple.LiveStreamViewerSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface LiveStreamViewerMapper {

    LiveStreamViewerMapper INSTANCE = Mappers.getMapper(LiveStreamViewerMapper.class);

    LiveStreamViewerDto toDto(LiveStreamViewer livestreamviewer);

    LiveStreamViewerSimpleDto toSimpleDto(LiveStreamViewer livestreamviewer);

    @InheritInverseConfiguration
    LiveStreamViewer toEntity(LiveStreamViewerDto livestreamviewerDto);

    List<LiveStreamViewerDto> toDtoList(List<LiveStreamViewer> livestreamviewerList);

    List<LiveStreamViewer> toEntityList(List<LiveStreamViewerDto> livestreamviewerDtoList);

    void updateEntityFromDto(LiveStreamViewerDto dto, @MappingTarget LiveStreamViewer entity);

}