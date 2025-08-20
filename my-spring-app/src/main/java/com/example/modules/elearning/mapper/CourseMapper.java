package com.example.modules.elearning.mapper;

import com.example.modules.elearning.model.Course;
import com.example.modules.elearning.dto.CourseDto;
import com.example.modules.elearning.dtosimple.CourseSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    CourseDto toDto(Course course);

    CourseSimpleDto toSimpleDto(Course course);

    @InheritInverseConfiguration
    Course toEntity(CourseDto courseDto);

    List<CourseDto> toDtoList(List<Course> courseList);

    List<Course> toEntityList(List<CourseDto> courseDtoList);

    void updateEntityFromDto(CourseDto dto, @MappingTarget Course entity);

}