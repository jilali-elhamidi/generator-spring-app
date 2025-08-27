package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.BookSeries;
import com.example.modules.entertainment_ecosystem.dto.BookSeriesDto;
import com.example.modules.entertainment_ecosystem.dtosimple.BookSeriesSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface BookSeriesMapper extends BaseMapper<BookSeries, BookSeriesDto, BookSeriesSimpleDto> {
}