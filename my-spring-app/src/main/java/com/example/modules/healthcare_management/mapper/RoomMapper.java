package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Room;
import com.example.modules.healthcare_management.dto.RoomDto;
import com.example.modules.healthcare_management.dtosimple.RoomSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface RoomMapper extends BaseMapper<Room, RoomDto, RoomSimpleDto> {
}