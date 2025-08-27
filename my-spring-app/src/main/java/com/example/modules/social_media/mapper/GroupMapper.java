package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Group;
import com.example.modules.social_media.dto.GroupDto;
import com.example.modules.social_media.dtosimple.GroupSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface GroupMapper extends BaseMapper<Group, GroupDto, GroupSimpleDto> {
}