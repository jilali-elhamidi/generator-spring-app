package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MovieFormat;
import com.example.modules.entertainment_ecosystem.dto.MovieFormatDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieFormatSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MovieFormatMapper {

    MovieFormatMapper INSTANCE = Mappers.getMapper(MovieFormatMapper.class);

    MovieFormatDto toDto(MovieFormat movieformat);

    MovieFormatSimpleDto toSimpleDto(MovieFormat movieformat);

    @InheritInverseConfiguration
    MovieFormat toEntity(MovieFormatDto movieformatDto);

    List<MovieFormatDto> toDtoList(List<MovieFormat> movieformatList);

    List<MovieFormat> toEntityList(List<MovieFormatDto> movieformatDtoList);

    void updateEntityFromDto(MovieFormatDto dto, @MappingTarget MovieFormat entity);

}