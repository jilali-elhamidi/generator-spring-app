package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.dto.TVShowDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TVShowMapper {

    TVShowMapper INSTANCE = Mappers.getMapper(TVShowMapper.class);

    TVShowDto toDto(TVShow tvshow);

    TVShowSimpleDto toSimpleDto(TVShow tvshow);

    @InheritInverseConfiguration
    TVShow toEntity(TVShowDto tvshowDto);

    List<TVShowDto> toDtoList(List<TVShow> tvshowList);

    List<TVShow> toEntityList(List<TVShowDto> tvshowDtoList);

}