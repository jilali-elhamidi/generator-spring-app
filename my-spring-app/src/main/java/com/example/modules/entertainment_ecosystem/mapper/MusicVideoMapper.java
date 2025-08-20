package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MusicVideo;
import com.example.modules.entertainment_ecosystem.dto.MusicVideoDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicVideoSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MusicVideoMapper {

    MusicVideoMapper INSTANCE = Mappers.getMapper(MusicVideoMapper.class);

    MusicVideoDto toDto(MusicVideo musicvideo);

    MusicVideoSimpleDto toSimpleDto(MusicVideo musicvideo);

    @InheritInverseConfiguration
    MusicVideo toEntity(MusicVideoDto musicvideoDto);

    List<MusicVideoDto> toDtoList(List<MusicVideo> musicvideoList);

    List<MusicVideo> toEntityList(List<MusicVideoDto> musicvideoDtoList);

    void updateEntityFromDto(MusicVideoDto dto, @MappingTarget MusicVideo entity);

}