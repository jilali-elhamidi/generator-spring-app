package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Booking;
import com.example.modules.entertainment_ecosystem.dto.BookingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.BookingSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    BookingDto toDto(Booking booking);

    BookingSimpleDto toSimpleDto(Booking booking);

    @InheritInverseConfiguration
    Booking toEntity(BookingDto bookingDto);

    List<BookingDto> toDtoList(List<Booking> bookingList);

    List<Booking> toEntityList(List<BookingDto> bookingDtoList);

    void updateEntityFromDto(BookingDto dto, @MappingTarget Booking entity);

}