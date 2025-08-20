package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserBlockedList;
import com.example.modules.entertainment_ecosystem.dto.UserBlockedListDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserBlockedListSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserBlockedListMapper {

    UserBlockedListMapper INSTANCE = Mappers.getMapper(UserBlockedListMapper.class);

    UserBlockedListDto toDto(UserBlockedList userblockedlist);

    UserBlockedListSimpleDto toSimpleDto(UserBlockedList userblockedlist);

    @InheritInverseConfiguration
    UserBlockedList toEntity(UserBlockedListDto userblockedlistDto);

    List<UserBlockedListDto> toDtoList(List<UserBlockedList> userblockedlistList);

    List<UserBlockedList> toEntityList(List<UserBlockedListDto> userblockedlistDtoList);

    void updateEntityFromDto(UserBlockedListDto dto, @MappingTarget UserBlockedList entity);

}