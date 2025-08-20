package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.dto.MusicTrackDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicTrackSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MusicTrackMapper {

    MusicTrackMapper INSTANCE = Mappers.getMapper(MusicTrackMapper.class);

    MusicTrackDto toDto(MusicTrack musictrack);

    MusicTrackSimpleDto toSimpleDto(MusicTrack musictrack);

    @InheritInverseConfiguration
    MusicTrack toEntity(MusicTrackDto musictrackDto);

    List<MusicTrackDto> toDtoList(List<MusicTrack> musictrackList);

    List<MusicTrack> toEntityList(List<MusicTrackDto> musictrackDtoList);

    void updateEntityFromDto(MusicTrackDto dto, @MappingTarget MusicTrack entity);

}