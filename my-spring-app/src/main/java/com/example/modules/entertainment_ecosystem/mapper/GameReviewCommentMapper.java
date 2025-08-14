package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameReviewComment;
import com.example.modules.entertainment_ecosystem.dto.GameReviewCommentDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewCommentSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GameReviewCommentMapper {

    GameReviewCommentMapper INSTANCE = Mappers.getMapper(GameReviewCommentMapper.class);

    GameReviewCommentDto toDto(GameReviewComment gamereviewcomment);

    GameReviewCommentSimpleDto toSimpleDto(GameReviewComment gamereviewcomment);

    @InheritInverseConfiguration
    GameReviewComment toEntity(GameReviewCommentDto gamereviewcommentDto);

    List<GameReviewCommentDto> toDtoList(List<GameReviewComment> gamereviewcommentList);

    List<GameReviewComment> toEntityList(List<GameReviewCommentDto> gamereviewcommentDtoList);

    void updateEntityFromDto(GameReviewCommentDto dto, @MappingTarget GameReviewComment entity);

}