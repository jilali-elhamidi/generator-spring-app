package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ForumCategory;
import com.example.modules.entertainment_ecosystem.dto.ForumCategoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ForumCategorySimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ForumCategoryMapper extends BaseMapper<ForumCategory, ForumCategoryDto, ForumCategorySimpleDto> {
}