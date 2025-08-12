package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.dto.MovieDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MovieDto toDto(Movie movie);

    MovieSimpleDto toSimpleDto(Movie movie);

    @InheritInverseConfiguration
    Movie toEntity(MovieDto movieDto);

    List<MovieDto> toDtoList(List<Movie> movieList);

    List<Movie> toEntityList(List<MovieDto> movieDtoList);

}