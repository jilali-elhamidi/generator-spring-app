package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserLoyaltyProgram;
import com.example.modules.entertainment_ecosystem.dto.UserLoyaltyProgramDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserLoyaltyProgramSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserLoyaltyProgramMapper extends BaseMapper<UserLoyaltyProgram, UserLoyaltyProgramDto, UserLoyaltyProgramSimpleDto> {
}