package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameCurrency;
import com.example.modules.entertainment_ecosystem.dto.GameCurrencyDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameCurrencySimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GameCurrencyMapper {

    GameCurrencyMapper INSTANCE = Mappers.getMapper(GameCurrencyMapper.class);

    GameCurrencyDto toDto(GameCurrency gamecurrency);

    GameCurrencySimpleDto toSimpleDto(GameCurrency gamecurrency);

    @InheritInverseConfiguration
    GameCurrency toEntity(GameCurrencyDto gamecurrencyDto);

    List<GameCurrencyDto> toDtoList(List<GameCurrency> gamecurrencyList);

    List<GameCurrency> toEntityList(List<GameCurrencyDto> gamecurrencyDtoList);

    void updateEntityFromDto(GameCurrencyDto dto, @MappingTarget GameCurrency entity);

}