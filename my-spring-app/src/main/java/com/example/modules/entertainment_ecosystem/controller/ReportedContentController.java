package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ReportedContentDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReportedContentSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ReportedContent;
import com.example.modules.entertainment_ecosystem.mapper.ReportedContentMapper;
import com.example.modules.entertainment_ecosystem.service.ReportedContentService;
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
 * Controller for managing ReportedContent entities.
 */
@RestController
@RequestMapping("/api/reportedcontents")
public class ReportedContentController extends BaseController<ReportedContent, ReportedContentDto, ReportedContentSimpleDto> {

    public ReportedContentController(ReportedContentService reportedcontentService,
                                    ReportedContentMapper reportedcontentMapper) {
        super(reportedcontentService, reportedcontentMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ReportedContentDto>> getAllReportedContents(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ReportedContentDto>> searchReportedContents(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ReportedContent.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportedContentDto> getReportedContentById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ReportedContentDto> createReportedContent(
            @Valid @RequestBody ReportedContentDto reportedcontentDto,
            UriComponentsBuilder uriBuilder) {

        ReportedContent entity = mapper.toEntity(reportedcontentDto);
        ReportedContent saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/reportedcontents/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ReportedContentDto>> createAllReportedContents(
            @Valid @RequestBody List<ReportedContentDto> reportedcontentDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ReportedContent> entities = mapper.toEntityList(reportedcontentDtoList);
        List<ReportedContent> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/reportedcontents").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportedContentDto> updateReportedContent(
            @PathVariable Long id,
            @Valid @RequestBody ReportedContentDto reportedcontentDto) {

        ReportedContent entityToUpdate = mapper.toEntity(reportedcontentDto);
        ReportedContent updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReportedContent(@PathVariable Long id) {
        return doDelete(id);
    }
}