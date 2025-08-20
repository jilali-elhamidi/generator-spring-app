package com.example.modules.project_management.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.project_management.model.Attachment;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends BaseRepository<Attachment, Long> {

}
