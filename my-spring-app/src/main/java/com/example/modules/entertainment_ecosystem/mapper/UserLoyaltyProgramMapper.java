package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserLoyaltyProgram;
import com.example.modules.entertainment_ecosystem.dto.UserLoyaltyProgramDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserLoyaltyProgramSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserLoyaltyProgramMapper {

    UserLoyaltyProgramMapper INSTANCE = Mappers.getMapper(UserLoyaltyProgramMapper.class);

    UserLoyaltyProgramDto toDto(UserLoyaltyProgram userloyaltyprogram);

    UserLoyaltyProgramSimpleDto toSimpleDto(UserLoyaltyProgram userloyaltyprogram);

    @InheritInverseConfiguration
    UserLoyaltyProgram toEntity(UserLoyaltyProgramDto userloyaltyprogramDto);

    List<UserLoyaltyProgramDto> toDtoList(List<UserLoyaltyProgram> userloyaltyprogramList);

    List<UserLoyaltyProgram> toEntityList(List<UserLoyaltyProgramDto> userloyaltyprogramDtoList);

    void updateEntityFromDto(UserLoyaltyProgramDto dto, @MappingTarget UserLoyaltyProgram entity);

}