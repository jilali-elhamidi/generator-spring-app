package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.dto.UserProfileDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserProfileMapper extends BaseMapper<UserProfile, UserProfileDto, UserProfileSimpleDto> {
}