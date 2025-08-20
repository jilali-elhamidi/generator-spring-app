package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.Document;
import com.example.modules.project_management.repository.DocumentRepository;
import com.example.modules.project_management.model.Project;
import com.example.modules.project_management.repository.ProjectRepository;
import com.example.modules.project_management.model.TeamMember;
import com.example.modules.project_management.repository.TeamMemberRepository;
import com.example.modules.project_management.model.DocumentType;
import com.example.modules.project_management.repository.DocumentTypeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class DocumentService extends BaseService<Document> {

    protected final DocumentRepository documentRepository;
    private final ProjectRepository projectRepository;
    private final TeamMemberRepository uploadedByRepository;
    private final DocumentTypeRepository typeRepository;

    public DocumentService(DocumentRepository repository, ProjectRepository projectRepository, TeamMemberRepository uploadedByRepository, DocumentTypeRepository typeRepository)
    {
        super(repository);
        this.documentRepository = repository;
        this.projectRepository = projectRepository;
        this.uploadedByRepository = uploadedByRepository;
        this.typeRepository = typeRepository;
    }

    @Override
    public Document save(Document document) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (document.getProject() != null &&
            document.getProject().getId() != null) {

            Project existingProject = projectRepository.findById(
                document.getProject().getId()
            ).orElseThrow(() -> new RuntimeException("Project not found"));

            document.setProject(existingProject);
        }
        
        if (document.getUploadedBy() != null &&
            document.getUploadedBy().getId() != null) {

            TeamMember existingUploadedBy = uploadedByRepository.findById(
                document.getUploadedBy().getId()
            ).orElseThrow(() -> new RuntimeException("TeamMember not found"));

            document.setUploadedBy(existingUploadedBy);
        }
        
        if (document.getType() != null &&
            document.getType().getId() != null) {

            DocumentType existingType = typeRepository.findById(
                document.getType().getId()
            ).orElseThrow(() -> new RuntimeException("DocumentType not found"));

            document.setType(existingType);
        }
        
    // ---------- OneToOne ----------
    return documentRepository.save(document);
}


    public Document update(Long id, Document documentRequest) {
        Document existing = documentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Document not found"));

    // Copier les champs simples
        existing.setTitle(documentRequest.getTitle());
        existing.setFileUrl(documentRequest.getFileUrl());
        existing.setUploadDate(documentRequest.getUploadDate());

    // ---------- Relations ManyToOne ----------
        if (documentRequest.getProject() != null &&
            documentRequest.getProject().getId() != null) {

            Project existingProject = projectRepository.findById(
                documentRequest.getProject().getId()
            ).orElseThrow(() -> new RuntimeException("Project not found"));

            existing.setProject(existingProject);
        } else {
            existing.setProject(null);
        }
        
        if (documentRequest.getUploadedBy() != null &&
            documentRequest.getUploadedBy().getId() != null) {

            TeamMember existingUploadedBy = uploadedByRepository.findById(
                documentRequest.getUploadedBy().getId()
            ).orElseThrow(() -> new RuntimeException("TeamMember not found"));

            existing.setUploadedBy(existingUploadedBy);
        } else {
            existing.setUploadedBy(null);
        }
        
        if (documentRequest.getType() != null &&
            documentRequest.getType().getId() != null) {

            DocumentType existingType = typeRepository.findById(
                documentRequest.getType().getId()
            ).orElseThrow(() -> new RuntimeException("DocumentType not found"));

            existing.setType(existingType);
        } else {
            existing.setType(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return documentRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Document> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Document entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getProject() != null) {
            entity.setProject(null);
        }
        
        if (entity.getUploadedBy() != null) {
            entity.setUploadedBy(null);
        }
        
        if (entity.getType() != null) {
            entity.setType(null);
        }
        
        repository.delete(entity);
        return true;
    }
}