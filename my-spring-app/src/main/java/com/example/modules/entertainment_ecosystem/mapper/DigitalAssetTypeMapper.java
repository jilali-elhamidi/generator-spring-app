package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.DigitalAssetType;
import com.example.modules.entertainment_ecosystem.dto.DigitalAssetTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.DigitalAssetTypeSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface DigitalAssetTypeMapper extends BaseMapper<DigitalAssetType, DigitalAssetTypeDto, DigitalAssetTypeSimpleDto> {
}