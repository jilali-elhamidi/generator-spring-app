package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.BookSeries;
import com.example.modules.entertainment_ecosystem.dto.BookSeriesDto;
import com.example.modules.entertainment_ecosystem.dtosimple.BookSeriesSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface BookSeriesMapper {

    BookSeriesMapper INSTANCE = Mappers.getMapper(BookSeriesMapper.class);

    BookSeriesDto toDto(BookSeries bookseries);

    BookSeriesSimpleDto toSimpleDto(BookSeries bookseries);

    @InheritInverseConfiguration
    BookSeries toEntity(BookSeriesDto bookseriesDto);

    List<BookSeriesDto> toDtoList(List<BookSeries> bookseriesList);

    List<BookSeries> toEntityList(List<BookSeriesDto> bookseriesDtoList);

    void updateEntityFromDto(BookSeriesDto dto, @MappingTarget BookSeries entity);

}