package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ContentRatingAgeGroup;
import com.example.modules.entertainment_ecosystem.dto.ContentRatingAgeGroupDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentRatingAgeGroupSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ContentRatingAgeGroupMapper {

    ContentRatingAgeGroupMapper INSTANCE = Mappers.getMapper(ContentRatingAgeGroupMapper.class);

    ContentRatingAgeGroupDto toDto(ContentRatingAgeGroup contentratingagegroup);

    ContentRatingAgeGroupSimpleDto toSimpleDto(ContentRatingAgeGroup contentratingagegroup);

    @InheritInverseConfiguration
    ContentRatingAgeGroup toEntity(ContentRatingAgeGroupDto contentratingagegroupDto);

    List<ContentRatingAgeGroupDto> toDtoList(List<ContentRatingAgeGroup> contentratingagegroupList);

    List<ContentRatingAgeGroup> toEntityList(List<ContentRatingAgeGroupDto> contentratingagegroupDtoList);

    void updateEntityFromDto(ContentRatingAgeGroupDto dto, @MappingTarget ContentRatingAgeGroup entity);

}