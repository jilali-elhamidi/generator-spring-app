package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Publisher;
import com.example.modules.entertainment_ecosystem.dto.PublisherDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PublisherSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface PublisherMapper extends BaseMapper<Publisher, PublisherDto, PublisherSimpleDto> {
}