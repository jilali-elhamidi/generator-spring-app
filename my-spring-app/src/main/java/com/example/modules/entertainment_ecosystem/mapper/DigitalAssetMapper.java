package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.DigitalAsset;
import com.example.modules.entertainment_ecosystem.dto.DigitalAssetDto;
import com.example.modules.entertainment_ecosystem.dtosimple.DigitalAssetSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface DigitalAssetMapper extends BaseMapper<DigitalAsset, DigitalAssetDto, DigitalAssetSimpleDto> {
}