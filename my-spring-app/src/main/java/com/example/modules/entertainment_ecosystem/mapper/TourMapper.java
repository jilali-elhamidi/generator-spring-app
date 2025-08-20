package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Tour;
import com.example.modules.entertainment_ecosystem.dto.TourDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TourSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TourMapper {

    TourMapper INSTANCE = Mappers.getMapper(TourMapper.class);

    TourDto toDto(Tour tour);

    TourSimpleDto toSimpleDto(Tour tour);

    @InheritInverseConfiguration
    Tour toEntity(TourDto tourDto);

    List<TourDto> toDtoList(List<Tour> tourList);

    List<Tour> toEntityList(List<TourDto> tourDtoList);

    void updateEntityFromDto(TourDto dto, @MappingTarget Tour entity);

}