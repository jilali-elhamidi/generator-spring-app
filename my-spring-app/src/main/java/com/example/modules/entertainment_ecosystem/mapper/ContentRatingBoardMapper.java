package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ContentRatingBoard;
import com.example.modules.entertainment_ecosystem.dto.ContentRatingBoardDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentRatingBoardSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ContentRatingBoardMapper {

    ContentRatingBoardMapper INSTANCE = Mappers.getMapper(ContentRatingBoardMapper.class);

    ContentRatingBoardDto toDto(ContentRatingBoard contentratingboard);

    ContentRatingBoardSimpleDto toSimpleDto(ContentRatingBoard contentratingboard);

    @InheritInverseConfiguration
    ContentRatingBoard toEntity(ContentRatingBoardDto contentratingboardDto);

    List<ContentRatingBoardDto> toDtoList(List<ContentRatingBoard> contentratingboardList);

    List<ContentRatingBoard> toEntityList(List<ContentRatingBoardDto> contentratingboardDtoList);

    void updateEntityFromDto(ContentRatingBoardDto dto, @MappingTarget ContentRatingBoard entity);

}