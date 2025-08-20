package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameReviewUpvote;
import com.example.modules.entertainment_ecosystem.dto.GameReviewUpvoteDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewUpvoteSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GameReviewUpvoteMapper {

    GameReviewUpvoteMapper INSTANCE = Mappers.getMapper(GameReviewUpvoteMapper.class);

    GameReviewUpvoteDto toDto(GameReviewUpvote gamereviewupvote);

    GameReviewUpvoteSimpleDto toSimpleDto(GameReviewUpvote gamereviewupvote);

    @InheritInverseConfiguration
    GameReviewUpvote toEntity(GameReviewUpvoteDto gamereviewupvoteDto);

    List<GameReviewUpvoteDto> toDtoList(List<GameReviewUpvote> gamereviewupvoteList);

    List<GameReviewUpvote> toEntityList(List<GameReviewUpvoteDto> gamereviewupvoteDtoList);

    void updateEntityFromDto(GameReviewUpvoteDto dto, @MappingTarget GameReviewUpvote entity);

}