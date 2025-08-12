package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.dto.PostDto;
import com.example.modules.social_media.dtosimple.PostSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDto toDto(Post post);

    PostSimpleDto toSimpleDto(Post post);

    @InheritInverseConfiguration
    Post toEntity(PostDto postDto);

    List<PostDto> toDtoList(List<Post> postList);

    List<Post> toEntityList(List<PostDto> postDtoList);

}