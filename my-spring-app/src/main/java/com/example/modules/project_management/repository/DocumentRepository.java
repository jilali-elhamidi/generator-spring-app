package com.example.modules.project_management.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.project_management.model.Document;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends BaseRepository<Document, Long> {
}
