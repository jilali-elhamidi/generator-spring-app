package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.dto.VideoGameDto;
import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface VideoGameMapper {

    VideoGameMapper INSTANCE = Mappers.getMapper(VideoGameMapper.class);

    VideoGameDto toDto(VideoGame videogame);

    VideoGameSimpleDto toSimpleDto(VideoGame videogame);

    @InheritInverseConfiguration
    VideoGame toEntity(VideoGameDto videogameDto);

    List<VideoGameDto> toDtoList(List<VideoGame> videogameList);

    List<VideoGame> toEntityList(List<VideoGameDto> videogameDtoList);

}