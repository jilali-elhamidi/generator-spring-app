package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.dto.ForumPostDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ForumPostSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ForumPostMapper extends BaseMapper<ForumPost, ForumPostDto, ForumPostSimpleDto> {
}