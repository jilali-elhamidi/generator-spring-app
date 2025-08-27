package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserWallet;
import com.example.modules.entertainment_ecosystem.dto.UserWalletDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserWalletSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserWalletMapper extends BaseMapper<UserWallet, UserWalletDto, UserWalletSimpleDto> {
}