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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class DocumentService extends BaseService<Document> {

    protected final DocumentRepository documentRepository;
    
    protected final ProjectRepository projectRepository;
    
    protected final TeamMemberRepository uploadedByRepository;
    
    protected final DocumentTypeRepository typeRepository;
    

    public DocumentService(DocumentRepository repository, ProjectRepository projectRepository, TeamMemberRepository uploadedByRepository, DocumentTypeRepository typeRepository)
    {
        super(repository);
        this.documentRepository = repository;
        
        this.projectRepository = projectRepository;
        
        this.uploadedByRepository = uploadedByRepository;
        
        this.typeRepository = typeRepository;
        
    }

    @Transactional
    @Override
    public Document save(Document document) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (document.getProject() != null) {
            if (document.getProject().getId() != null) {
                Project existingProject = projectRepository.findById(
                    document.getProject().getId()
                ).orElseThrow(() -> new RuntimeException("Project not found with id "
                    + document.getProject().getId()));
                document.setProject(existingProject);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Project newProject = projectRepository.save(document.getProject());
                document.setProject(newProject);
            }
        }
        
        if (document.getUploadedBy() != null) {
            if (document.getUploadedBy().getId() != null) {
                TeamMember existingUploadedBy = uploadedByRepository.findById(
                    document.getUploadedBy().getId()
                ).orElseThrow(() -> new RuntimeException("TeamMember not found with id "
                    + document.getUploadedBy().getId()));
                document.setUploadedBy(existingUploadedBy);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                TeamMember newUploadedBy = uploadedByRepository.save(document.getUploadedBy());
                document.setUploadedBy(newUploadedBy);
            }
        }
        
        if (document.getType() != null) {
            if (document.getType().getId() != null) {
                DocumentType existingType = typeRepository.findById(
                    document.getType().getId()
                ).orElseThrow(() -> new RuntimeException("DocumentType not found with id "
                    + document.getType().getId()));
                document.setType(existingType);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                DocumentType newType = typeRepository.save(document.getType());
                document.setType(newType);
            }
        }
        
    // ---------- OneToOne ----------
    return documentRepository.save(document);
}

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return documentRepository.save(existing);
}

    // Pagination simple
    public Page<Document> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Document> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Document.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Document> saveAll(List<Document> documentList) {
        return super.saveAll(documentList);
    }

}