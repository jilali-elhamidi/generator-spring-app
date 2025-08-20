package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseReview;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseReviewDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseReviewSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MerchandiseReviewMapper {

    MerchandiseReviewMapper INSTANCE = Mappers.getMapper(MerchandiseReviewMapper.class);

    MerchandiseReviewDto toDto(MerchandiseReview merchandisereview);

    MerchandiseReviewSimpleDto toSimpleDto(MerchandiseReview merchandisereview);

    @InheritInverseConfiguration
    MerchandiseReview toEntity(MerchandiseReviewDto merchandisereviewDto);

    List<MerchandiseReviewDto> toDtoList(List<MerchandiseReview> merchandisereviewList);

    List<MerchandiseReview> toEntityList(List<MerchandiseReviewDto> merchandisereviewDtoList);

    void updateEntityFromDto(MerchandiseReviewDto dto, @MappingTarget MerchandiseReview entity);

}