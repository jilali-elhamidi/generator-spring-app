package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ReviewComment;
import com.example.modules.entertainment_ecosystem.dto.ReviewCommentDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewCommentSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ReviewCommentMapper {

    ReviewCommentMapper INSTANCE = Mappers.getMapper(ReviewCommentMapper.class);

    ReviewCommentDto toDto(ReviewComment reviewcomment);

    ReviewCommentSimpleDto toSimpleDto(ReviewComment reviewcomment);

    @InheritInverseConfiguration
    ReviewComment toEntity(ReviewCommentDto reviewcommentDto);

    List<ReviewCommentDto> toDtoList(List<ReviewComment> reviewcommentList);

    List<ReviewComment> toEntityList(List<ReviewCommentDto> reviewcommentDtoList);

    void updateEntityFromDto(ReviewCommentDto dto, @MappingTarget ReviewComment entity);

}