package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Room;
import com.example.modules.healthcare_management.dto.RoomDto;
import com.example.modules.healthcare_management.dtosimple.RoomSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    RoomDto toDto(Room room);

    RoomSimpleDto toSimpleDto(Room room);

    @InheritInverseConfiguration
    Room toEntity(RoomDto roomDto);

    List<RoomDto> toDtoList(List<Room> roomList);

    List<Room> toEntityList(List<RoomDto> roomDtoList);

    void updateEntityFromDto(RoomDto dto, @MappingTarget Room entity);

}