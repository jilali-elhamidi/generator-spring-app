package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Tour;
import com.example.modules.entertainment_ecosystem.dto.TourDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TourSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface TourMapper extends BaseMapper<Tour, TourDto, TourSimpleDto> {
}