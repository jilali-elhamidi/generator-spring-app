package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.TVShowMerchandise;
import com.example.modules.entertainment_ecosystem.dto.TVShowMerchandiseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TVShowMerchandiseSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TVShowMerchandiseMapper {

    TVShowMerchandiseMapper INSTANCE = Mappers.getMapper(TVShowMerchandiseMapper.class);

    TVShowMerchandiseDto toDto(TVShowMerchandise tvshowmerchandise);

    TVShowMerchandiseSimpleDto toSimpleDto(TVShowMerchandise tvshowmerchandise);

    @InheritInverseConfiguration
    TVShowMerchandise toEntity(TVShowMerchandiseDto tvshowmerchandiseDto);

    List<TVShowMerchandiseDto> toDtoList(List<TVShowMerchandise> tvshowmerchandiseList);

    List<TVShowMerchandise> toEntityList(List<TVShowMerchandiseDto> tvshowmerchandiseDtoList);

    void updateEntityFromDto(TVShowMerchandiseDto dto, @MappingTarget TVShowMerchandise entity);

}