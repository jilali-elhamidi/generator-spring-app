package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MusicGenreCategory;
import com.example.modules.entertainment_ecosystem.dto.MusicGenreCategoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicGenreCategorySimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MusicGenreCategoryMapper {

    MusicGenreCategoryMapper INSTANCE = Mappers.getMapper(MusicGenreCategoryMapper.class);

    MusicGenreCategoryDto toDto(MusicGenreCategory musicgenrecategory);

    MusicGenreCategorySimpleDto toSimpleDto(MusicGenreCategory musicgenrecategory);

    @InheritInverseConfiguration
    MusicGenreCategory toEntity(MusicGenreCategoryDto musicgenrecategoryDto);

    List<MusicGenreCategoryDto> toDtoList(List<MusicGenreCategory> musicgenrecategoryList);

    List<MusicGenreCategory> toEntityList(List<MusicGenreCategoryDto> musicgenrecategoryDtoList);

    void updateEntityFromDto(MusicGenreCategoryDto dto, @MappingTarget MusicGenreCategory entity);

}