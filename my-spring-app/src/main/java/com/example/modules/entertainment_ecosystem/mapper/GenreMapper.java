package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.dto.GenreDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GenreSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    GenreDto toDto(Genre genre);

    GenreSimpleDto toSimpleDto(Genre genre);

    @InheritInverseConfiguration
    Genre toEntity(GenreDto genreDto);

    List<GenreDto> toDtoList(List<Genre> genreList);

    List<Genre> toEntityList(List<GenreDto> genreDtoList);

}