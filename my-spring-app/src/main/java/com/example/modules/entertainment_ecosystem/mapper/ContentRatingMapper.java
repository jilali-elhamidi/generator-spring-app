package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ContentRating;
import com.example.modules.entertainment_ecosystem.dto.ContentRatingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentRatingSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ContentRatingMapper {

    ContentRatingMapper INSTANCE = Mappers.getMapper(ContentRatingMapper.class);

    ContentRatingDto toDto(ContentRating contentrating);

    ContentRatingSimpleDto toSimpleDto(ContentRating contentrating);

    @InheritInverseConfiguration
    ContentRating toEntity(ContentRatingDto contentratingDto);

    List<ContentRatingDto> toDtoList(List<ContentRating> contentratingList);

    List<ContentRating> toEntityList(List<ContentRatingDto> contentratingDtoList);

    void updateEntityFromDto(ContentRatingDto dto, @MappingTarget ContentRating entity);

}