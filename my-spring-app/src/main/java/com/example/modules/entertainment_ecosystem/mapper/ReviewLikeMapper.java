package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ReviewLike;
import com.example.modules.entertainment_ecosystem.dto.ReviewLikeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewLikeSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ReviewLikeMapper {

    ReviewLikeMapper INSTANCE = Mappers.getMapper(ReviewLikeMapper.class);

    ReviewLikeDto toDto(ReviewLike reviewlike);

    ReviewLikeSimpleDto toSimpleDto(ReviewLike reviewlike);

    @InheritInverseConfiguration
    ReviewLike toEntity(ReviewLikeDto reviewlikeDto);

    List<ReviewLikeDto> toDtoList(List<ReviewLike> reviewlikeList);

    List<ReviewLike> toEntityList(List<ReviewLikeDto> reviewlikeDtoList);

}