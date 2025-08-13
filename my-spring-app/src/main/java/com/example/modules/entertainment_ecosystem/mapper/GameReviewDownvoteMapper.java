package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;
import com.example.modules.entertainment_ecosystem.dto.GameReviewDownvoteDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewDownvoteSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GameReviewDownvoteMapper {

    GameReviewDownvoteMapper INSTANCE = Mappers.getMapper(GameReviewDownvoteMapper.class);

    GameReviewDownvoteDto toDto(GameReviewDownvote gamereviewdownvote);

    GameReviewDownvoteSimpleDto toSimpleDto(GameReviewDownvote gamereviewdownvote);

    @InheritInverseConfiguration
    GameReviewDownvote toEntity(GameReviewDownvoteDto gamereviewdownvoteDto);

    List<GameReviewDownvoteDto> toDtoList(List<GameReviewDownvote> gamereviewdownvoteList);

    List<GameReviewDownvote> toEntityList(List<GameReviewDownvoteDto> gamereviewdownvoteDtoList);

}