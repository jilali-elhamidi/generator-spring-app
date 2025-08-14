package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.dto.ReviewDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    ReviewDto toDto(Review review);

    ReviewSimpleDto toSimpleDto(Review review);

    @InheritInverseConfiguration
    Review toEntity(ReviewDto reviewDto);

    List<ReviewDto> toDtoList(List<Review> reviewList);

    List<Review> toEntityList(List<ReviewDto> reviewDtoList);

    void updateEntityFromDto(ReviewDto dto, @MappingTarget Review entity);

}