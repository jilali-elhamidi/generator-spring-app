package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.AdCampaign;
import com.example.modules.entertainment_ecosystem.dto.AdCampaignDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AdCampaignSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface AdCampaignMapper extends BaseMapper<AdCampaign, AdCampaignDto, AdCampaignSimpleDto> {
}