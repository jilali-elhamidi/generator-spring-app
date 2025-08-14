package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserSetting;
import com.example.modules.entertainment_ecosystem.dto.UserSettingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserSettingSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserSettingMapper {

    UserSettingMapper INSTANCE = Mappers.getMapper(UserSettingMapper.class);

    UserSettingDto toDto(UserSetting usersetting);

    UserSettingSimpleDto toSimpleDto(UserSetting usersetting);

    @InheritInverseConfiguration
    UserSetting toEntity(UserSettingDto usersettingDto);

    List<UserSettingDto> toDtoList(List<UserSetting> usersettingList);

    List<UserSetting> toEntityList(List<UserSettingDto> usersettingDtoList);

    void updateEntityFromDto(UserSettingDto dto, @MappingTarget UserSetting entity);

}