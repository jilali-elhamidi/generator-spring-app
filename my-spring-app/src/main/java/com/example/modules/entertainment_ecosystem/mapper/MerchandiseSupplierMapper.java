package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseSupplier;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseSupplierDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSupplierSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MerchandiseSupplierMapper extends BaseMapper<MerchandiseSupplier, MerchandiseSupplierDto, MerchandiseSupplierSimpleDto> {
}