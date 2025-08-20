package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserRating;
import com.example.modules.entertainment_ecosystem.dto.UserRatingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserRatingSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserRatingMapper {

    UserRatingMapper INSTANCE = Mappers.getMapper(UserRatingMapper.class);

    UserRatingDto toDto(UserRating userrating);

    UserRatingSimpleDto toSimpleDto(UserRating userrating);

    @InheritInverseConfiguration
    UserRating toEntity(UserRatingDto userratingDto);

    List<UserRatingDto> toDtoList(List<UserRating> userratingList);

    List<UserRating> toEntityList(List<UserRatingDto> userratingDtoList);

    void updateEntityFromDto(UserRatingDto dto, @MappingTarget UserRating entity);

}