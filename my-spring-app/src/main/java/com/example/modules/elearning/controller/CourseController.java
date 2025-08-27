package com.example.modules.elearning.controller;

import com.example.modules.elearning.dto.CourseDto;
import com.example.modules.elearning.dtosimple.CourseSimpleDto;
import com.example.modules.elearning.model.Course;
import com.example.modules.elearning.mapper.CourseMapper;
import com.example.modules.elearning.service.CourseService;
import com.example.core.controller.BaseController;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing Course entities.
 */
@RestController
@RequestMapping("/api/courses")
public class CourseController extends BaseController<Course, CourseDto, CourseSimpleDto> {

    public CourseController(CourseService courseService,
                                    CourseMapper courseMapper) {
        super(courseService, courseMapper);
    }

    @GetMapping
    public ResponseEntity<Page<CourseDto>> getAllCourses(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CourseDto>> searchCourses(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Course.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(
            @Valid @RequestBody CourseDto courseDto,
            UriComponentsBuilder uriBuilder) {

        Course entity = mapper.toEntity(courseDto);
        Course saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/courses/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<CourseDto>> createAllCourses(
            @Valid @RequestBody List<CourseDto> courseDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Course> entities = mapper.toEntityList(courseDtoList);
        List<Course> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/courses").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseDto courseDto) {

        Course entityToUpdate = mapper.toEntity(courseDto);
        Course updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        return doDelete(id);
    }
}