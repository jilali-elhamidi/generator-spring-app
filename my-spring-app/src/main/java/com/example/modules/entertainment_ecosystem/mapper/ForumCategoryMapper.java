package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ForumCategory;
import com.example.modules.entertainment_ecosystem.dto.ForumCategoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ForumCategorySimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ForumCategoryMapper {

    ForumCategoryMapper INSTANCE = Mappers.getMapper(ForumCategoryMapper.class);

    ForumCategoryDto toDto(ForumCategory forumcategory);

    ForumCategorySimpleDto toSimpleDto(ForumCategory forumcategory);

    @InheritInverseConfiguration
    ForumCategory toEntity(ForumCategoryDto forumcategoryDto);

    List<ForumCategoryDto> toDtoList(List<ForumCategory> forumcategoryList);

    List<ForumCategory> toEntityList(List<ForumCategoryDto> forumcategoryDtoList);

    void updateEntityFromDto(ForumCategoryDto dto, @MappingTarget ForumCategory entity);

}