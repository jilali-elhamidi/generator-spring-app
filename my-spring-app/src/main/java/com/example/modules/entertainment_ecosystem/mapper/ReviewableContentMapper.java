package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ReviewableContent;
import com.example.modules.entertainment_ecosystem.dto.ReviewableContentDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewableContentSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ReviewableContentMapper {

    ReviewableContentMapper INSTANCE = Mappers.getMapper(ReviewableContentMapper.class);

    ReviewableContentDto toDto(ReviewableContent reviewablecontent);

    ReviewableContentSimpleDto toSimpleDto(ReviewableContent reviewablecontent);

    @InheritInverseConfiguration
    ReviewableContent toEntity(ReviewableContentDto reviewablecontentDto);

    List<ReviewableContentDto> toDtoList(List<ReviewableContent> reviewablecontentList);

    List<ReviewableContent> toEntityList(List<ReviewableContentDto> reviewablecontentDtoList);

}