package com.example.core.controller;

import com.example.core.mapper.BaseMapper;
import com.example.core.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.Map;

public abstract class BaseController<E, D, SD> {

    protected final BaseService<E> service;
    protected final BaseMapper<E, D, SD> mapper;

    protected BaseController(BaseService<E> service, BaseMapper<E, D, SD> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // Helpers for CRUD + search to be called from subclass annotated methods
    protected ResponseEntity<Page<D>> doGetAll(Pageable pageable) {
        Page<E> page = service.findAll(pageable);
        return ResponseEntity.ok(page.map(mapper::toDto));
    }

    protected ResponseEntity<Page<D>> doSearch(Class<E> entityClass, Map<String, String> filters, Pageable pageable) {
        Page<E> page = service.search(entityClass, filters, pageable);
        return ResponseEntity.ok(page.map(mapper::toDto));
    }

    protected ResponseEntity<D> doGetById(Long id) {
        return service.findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    protected ResponseEntity<D> doCreate(D dto, URI location) {
        E entity = mapper.toEntity(dto);
        E saved = service.save(entity);
        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    protected ResponseEntity<List<D>> doCreateAll(List<D> dtos, URI location) {
        List<E> entities = mapper.toEntityList(dtos);
        List<E> saved = service.saveAll(entities);
        return ResponseEntity.created(location).body(mapper.toDtoList(saved));
    }

    protected ResponseEntity<D> doUpdate(Long id, D dto) {
        E toUpdate = mapper.toEntity(dto);
        E updated = service.update(id, toUpdate);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    protected ResponseEntity<Void> doDelete(Long id) {
        boolean deleted = service.deleteById(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
