package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.VideoGameRating;
import com.example.modules.entertainment_ecosystem.dto.VideoGameRatingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameRatingSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface VideoGameRatingMapper {

    VideoGameRatingMapper INSTANCE = Mappers.getMapper(VideoGameRatingMapper.class);

    VideoGameRatingDto toDto(VideoGameRating videogamerating);

    VideoGameRatingSimpleDto toSimpleDto(VideoGameRating videogamerating);

    @InheritInverseConfiguration
    VideoGameRating toEntity(VideoGameRatingDto videogameratingDto);

    List<VideoGameRatingDto> toDtoList(List<VideoGameRating> videogameratingList);

    List<VideoGameRating> toEntityList(List<VideoGameRatingDto> videogameratingDtoList);

}