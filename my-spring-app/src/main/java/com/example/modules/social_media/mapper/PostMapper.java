package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.dto.PostDto;
import com.example.modules.social_media.dto.PostSimpleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring") // L'attribut 'uses' est supprimé
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "media", ignore = true)
    @Mapping(target = "group", ignore = true)
    PostDto toDto(Post post);

    PostSimpleDto toSimpleDto(Post post);

    Post toEntity(PostDto postDto);

    List<PostDto> toDtoList(List<Post> postList);

    List<Post> toEntityList(List<PostDto> postDtoList);
}