package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseCollection;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseCollectionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseCollectionSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MerchandiseCollectionMapper extends BaseMapper<MerchandiseCollection, MerchandiseCollectionDto, MerchandiseCollectionSimpleDto> {
}