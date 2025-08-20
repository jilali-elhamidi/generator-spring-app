package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserWallet;
import com.example.modules.entertainment_ecosystem.dto.UserWalletDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserWalletSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserWalletMapper {

    UserWalletMapper INSTANCE = Mappers.getMapper(UserWalletMapper.class);

    UserWalletDto toDto(UserWallet userwallet);

    UserWalletSimpleDto toSimpleDto(UserWallet userwallet);

    @InheritInverseConfiguration
    UserWallet toEntity(UserWalletDto userwalletDto);

    List<UserWalletDto> toDtoList(List<UserWallet> userwalletList);

    List<UserWallet> toEntityList(List<UserWalletDto> userwalletDtoList);

    void updateEntityFromDto(UserWalletDto dto, @MappingTarget UserWallet entity);

}