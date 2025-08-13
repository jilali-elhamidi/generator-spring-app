package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.AdCampaign;
import com.example.modules.entertainment_ecosystem.dto.AdCampaignDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AdCampaignSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface AdCampaignMapper {

    AdCampaignMapper INSTANCE = Mappers.getMapper(AdCampaignMapper.class);

    AdCampaignDto toDto(AdCampaign adcampaign);

    AdCampaignSimpleDto toSimpleDto(AdCampaign adcampaign);

    @InheritInverseConfiguration
    AdCampaign toEntity(AdCampaignDto adcampaignDto);

    List<AdCampaignDto> toDtoList(List<AdCampaign> adcampaignList);

    List<AdCampaign> toEntityList(List<AdCampaignDto> adcampaignDtoList);

}