package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Comment;
import com.example.modules.social_media.dto.CommentDto;
import com.example.modules.social_media.dto.CommentSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface CommentMapper {

CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);


CommentDto toDto(Comment comment);

CommentSimpleDto toSimpleDto(Comment comment);

@InheritInverseConfiguration
Comment toEntity(CommentDto commentDto);

List<CommentDto> toDtoList(List<Comment> commentList);

    List<Comment> toEntityList(List<CommentDto> commentDtoList);
        }