package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MovieSoundtrack;
import com.example.modules.entertainment_ecosystem.dto.MovieSoundtrackDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieSoundtrackSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MovieSoundtrackMapper {

    MovieSoundtrackMapper INSTANCE = Mappers.getMapper(MovieSoundtrackMapper.class);

    MovieSoundtrackDto toDto(MovieSoundtrack moviesoundtrack);

    MovieSoundtrackSimpleDto toSimpleDto(MovieSoundtrack moviesoundtrack);

    @InheritInverseConfiguration
    MovieSoundtrack toEntity(MovieSoundtrackDto moviesoundtrackDto);

    List<MovieSoundtrackDto> toDtoList(List<MovieSoundtrack> moviesoundtrackList);

    List<MovieSoundtrack> toEntityList(List<MovieSoundtrackDto> moviesoundtrackDtoList);

    void updateEntityFromDto(MovieSoundtrackDto dto, @MappingTarget MovieSoundtrack entity);

}