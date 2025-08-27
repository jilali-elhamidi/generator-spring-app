package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserBlockedList;
import com.example.modules.entertainment_ecosystem.dto.UserBlockedListDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserBlockedListSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserBlockedListMapper extends BaseMapper<UserBlockedList, UserBlockedListDto, UserBlockedListSimpleDto> {
}