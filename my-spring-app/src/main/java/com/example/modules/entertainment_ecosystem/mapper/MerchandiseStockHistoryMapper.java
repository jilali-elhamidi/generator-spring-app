package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseStockHistory;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseStockHistoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseStockHistorySimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MerchandiseStockHistoryMapper {

    MerchandiseStockHistoryMapper INSTANCE = Mappers.getMapper(MerchandiseStockHistoryMapper.class);

    MerchandiseStockHistoryDto toDto(MerchandiseStockHistory merchandisestockhistory);

    MerchandiseStockHistorySimpleDto toSimpleDto(MerchandiseStockHistory merchandisestockhistory);

    @InheritInverseConfiguration
    MerchandiseStockHistory toEntity(MerchandiseStockHistoryDto merchandisestockhistoryDto);

    List<MerchandiseStockHistoryDto> toDtoList(List<MerchandiseStockHistory> merchandisestockhistoryList);

    List<MerchandiseStockHistory> toEntityList(List<MerchandiseStockHistoryDto> merchandisestockhistoryDtoList);

    void updateEntityFromDto(MerchandiseStockHistoryDto dto, @MappingTarget MerchandiseStockHistory entity);

}