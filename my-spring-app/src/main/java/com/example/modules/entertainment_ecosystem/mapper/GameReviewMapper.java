package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameReview;
import com.example.modules.entertainment_ecosystem.dto.GameReviewDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GameReviewMapper {

    GameReviewMapper INSTANCE = Mappers.getMapper(GameReviewMapper.class);

    GameReviewDto toDto(GameReview gamereview);

    GameReviewSimpleDto toSimpleDto(GameReview gamereview);

    @InheritInverseConfiguration
    GameReview toEntity(GameReviewDto gamereviewDto);

    List<GameReviewDto> toDtoList(List<GameReview> gamereviewList);

    List<GameReview> toEntityList(List<GameReviewDto> gamereviewDtoList);

}