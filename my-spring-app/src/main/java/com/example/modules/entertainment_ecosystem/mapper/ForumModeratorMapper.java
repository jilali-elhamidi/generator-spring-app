package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ForumModerator;
import com.example.modules.entertainment_ecosystem.dto.ForumModeratorDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ForumModeratorSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ForumModeratorMapper extends BaseMapper<ForumModerator, ForumModeratorDto, ForumModeratorSimpleDto> {
}