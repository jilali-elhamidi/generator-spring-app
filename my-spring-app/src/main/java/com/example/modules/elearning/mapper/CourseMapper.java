package com.example.modules.elearning.mapper;

import com.example.modules.elearning.model.Course;
import com.example.modules.elearning.dto.CourseDto;
import com.example.modules.elearning.dtosimple.CourseSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface CourseMapper extends BaseMapper<Course, CourseDto, CourseSimpleDto> {
}