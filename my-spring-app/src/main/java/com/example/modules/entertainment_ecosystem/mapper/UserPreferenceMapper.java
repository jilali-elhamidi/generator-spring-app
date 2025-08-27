package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserPreference;
import com.example.modules.entertainment_ecosystem.dto.UserPreferenceDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserPreferenceSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserPreferenceMapper extends BaseMapper<UserPreference, UserPreferenceDto, UserPreferenceSimpleDto> {
}