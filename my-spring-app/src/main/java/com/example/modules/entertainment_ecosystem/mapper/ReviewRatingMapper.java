package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ReviewRating;
import com.example.modules.entertainment_ecosystem.dto.ReviewRatingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewRatingSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ReviewRatingMapper {

    ReviewRatingMapper INSTANCE = Mappers.getMapper(ReviewRatingMapper.class);

    ReviewRatingDto toDto(ReviewRating reviewrating);

    ReviewRatingSimpleDto toSimpleDto(ReviewRating reviewrating);

    @InheritInverseConfiguration
    ReviewRating toEntity(ReviewRatingDto reviewratingDto);

    List<ReviewRatingDto> toDtoList(List<ReviewRating> reviewratingList);

    List<ReviewRating> toEntityList(List<ReviewRatingDto> reviewratingDtoList);

}