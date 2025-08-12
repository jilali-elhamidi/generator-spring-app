package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.dto.UserProfileDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    UserProfileDto toDto(UserProfile userprofile);

    UserProfileSimpleDto toSimpleDto(UserProfile userprofile);

    @InheritInverseConfiguration
    UserProfile toEntity(UserProfileDto userprofileDto);

    List<UserProfileDto> toDtoList(List<UserProfile> userprofileList);

    List<UserProfile> toEntityList(List<UserProfileDto> userprofileDtoList);

}