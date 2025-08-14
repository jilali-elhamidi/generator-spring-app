package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.PodcastCategory;
import com.example.modules.entertainment_ecosystem.dto.PodcastCategoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PodcastCategorySimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface PodcastCategoryMapper {

    PodcastCategoryMapper INSTANCE = Mappers.getMapper(PodcastCategoryMapper.class);

    PodcastCategoryDto toDto(PodcastCategory podcastcategory);

    PodcastCategorySimpleDto toSimpleDto(PodcastCategory podcastcategory);

    @InheritInverseConfiguration
    PodcastCategory toEntity(PodcastCategoryDto podcastcategoryDto);

    List<PodcastCategoryDto> toDtoList(List<PodcastCategory> podcastcategoryList);

    List<PodcastCategory> toEntityList(List<PodcastCategoryDto> podcastcategoryDtoList);

    void updateEntityFromDto(PodcastCategoryDto dto, @MappingTarget PodcastCategory entity);

}