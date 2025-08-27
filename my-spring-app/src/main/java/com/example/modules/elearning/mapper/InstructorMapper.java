package com.example.modules.elearning.mapper;

import com.example.modules.elearning.model.Instructor;
import com.example.modules.elearning.dto.InstructorDto;
import com.example.modules.elearning.dtosimple.InstructorSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface InstructorMapper extends BaseMapper<Instructor, InstructorDto, InstructorSimpleDto> {
}