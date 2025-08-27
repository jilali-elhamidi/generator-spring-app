package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Tag;
import com.example.modules.project_management.dto.TagDto;
import com.example.modules.project_management.dtosimple.TagSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends BaseMapper<Tag, TagDto, TagSimpleDto> {
}