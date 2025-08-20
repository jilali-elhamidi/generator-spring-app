package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MovieStudio;
import com.example.modules.entertainment_ecosystem.dto.MovieStudioDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieStudioSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MovieStudioMapper {

    MovieStudioMapper INSTANCE = Mappers.getMapper(MovieStudioMapper.class);

    MovieStudioDto toDto(MovieStudio moviestudio);

    MovieStudioSimpleDto toSimpleDto(MovieStudio moviestudio);

    @InheritInverseConfiguration
    MovieStudio toEntity(MovieStudioDto moviestudioDto);

    List<MovieStudioDto> toDtoList(List<MovieStudio> moviestudioList);

    List<MovieStudio> toEntityList(List<MovieStudioDto> moviestudioDtoList);

    void updateEntityFromDto(MovieStudioDto dto, @MappingTarget MovieStudio entity);

}