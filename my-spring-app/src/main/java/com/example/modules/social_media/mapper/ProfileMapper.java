package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.dto.ProfileDto;
import com.example.modules.social_media.dtosimple.ProfileSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ProfileMapper extends BaseMapper<Profile, ProfileDto, ProfileSimpleDto> {
}