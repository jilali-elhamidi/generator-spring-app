package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Shift;
import com.example.modules.entertainment_ecosystem.dto.ShiftDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ShiftSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ShiftMapper extends BaseMapper<Shift, ShiftDto, ShiftSimpleDto> {
}