package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MovieMerchandise;
import com.example.modules.entertainment_ecosystem.dto.MovieMerchandiseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieMerchandiseSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MovieMerchandiseMapper {

    MovieMerchandiseMapper INSTANCE = Mappers.getMapper(MovieMerchandiseMapper.class);

    MovieMerchandiseDto toDto(MovieMerchandise moviemerchandise);

    MovieMerchandiseSimpleDto toSimpleDto(MovieMerchandise moviemerchandise);

    @InheritInverseConfiguration
    MovieMerchandise toEntity(MovieMerchandiseDto moviemerchandiseDto);

    List<MovieMerchandiseDto> toDtoList(List<MovieMerchandise> moviemerchandiseList);

    List<MovieMerchandise> toEntityList(List<MovieMerchandiseDto> moviemerchandiseDtoList);

    void updateEntityFromDto(MovieMerchandiseDto dto, @MappingTarget MovieMerchandise entity);

}