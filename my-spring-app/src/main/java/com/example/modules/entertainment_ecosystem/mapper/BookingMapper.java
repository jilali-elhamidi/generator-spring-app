package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Booking;
import com.example.modules.entertainment_ecosystem.dto.BookingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.BookingSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface BookingMapper extends BaseMapper<Booking, BookingDto, BookingSimpleDto> {
}