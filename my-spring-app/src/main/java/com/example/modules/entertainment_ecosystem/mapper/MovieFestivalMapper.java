package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MovieFestival;
import com.example.modules.entertainment_ecosystem.dto.MovieFestivalDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieFestivalSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MovieFestivalMapper {

    MovieFestivalMapper INSTANCE = Mappers.getMapper(MovieFestivalMapper.class);

    MovieFestivalDto toDto(MovieFestival moviefestival);

    MovieFestivalSimpleDto toSimpleDto(MovieFestival moviefestival);

    @InheritInverseConfiguration
    MovieFestival toEntity(MovieFestivalDto moviefestivalDto);

    List<MovieFestivalDto> toDtoList(List<MovieFestival> moviefestivalList);

    List<MovieFestival> toEntityList(List<MovieFestivalDto> moviefestivalDtoList);

    void updateEntityFromDto(MovieFestivalDto dto, @MappingTarget MovieFestival entity);

}