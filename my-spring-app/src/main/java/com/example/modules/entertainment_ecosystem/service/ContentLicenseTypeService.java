package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ContentLicenseType;
import com.example.modules.entertainment_ecosystem.repository.ContentLicenseTypeRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;
import com.example.modules.entertainment_ecosystem.repository.StreamingContentLicenseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ContentLicenseTypeService extends BaseService<ContentLicenseType> {

    protected final ContentLicenseTypeRepository contentlicensetypeRepository;
    private final StreamingContentLicenseRepository licensesRepository;

    public ContentLicenseTypeService(ContentLicenseTypeRepository repository, StreamingContentLicenseRepository licensesRepository)
    {
        super(repository);
        this.contentlicensetypeRepository = repository;
        this.licensesRepository = licensesRepository;
    }

    @Override
    public ContentLicenseType save(ContentLicenseType contentlicensetype) {
    // ---------- OneToMany ----------
        if (contentlicensetype.getLicenses() != null) {
            List<StreamingContentLicense> managedLicenses = new ArrayList<>();
            for (StreamingContentLicense item : contentlicensetype.getLicenses()) {
                if (item.getId() != null) {
                    StreamingContentLicense existingItem = licensesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));

                     existingItem.setLicenseType(contentlicensetype);
                     managedLicenses.add(existingItem);
                } else {
                    item.setLicenseType(contentlicensetype);
                    managedLicenses.add(item);
                }
            }
            contentlicensetype.setLicenses(managedLicenses);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------

    return contentlicensetypeRepository.save(contentlicensetype);
}


    public ContentLicenseType update(Long id, ContentLicenseType contentlicensetypeRequest) {
        ContentLicenseType existing = contentlicensetypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentLicenseType not found"));

    // Copier les champs simples
        existing.setName(contentlicensetypeRequest.getName());
        existing.setDescription(contentlicensetypeRequest.getDescription());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getLicenses().clear();

        if (contentlicensetypeRequest.getLicenses() != null) {
            for (var item : contentlicensetypeRequest.getLicenses()) {
                StreamingContentLicense existingItem;
                if (item.getId() != null) {
                    existingItem = licensesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));
                } else {
                existingItem = item;
                }

                existingItem.setLicenseType(existing);
                existing.getLicenses().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------

    return contentlicensetypeRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<ContentLicenseType> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        ContentLicenseType entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getLicenses() != null) {
            for (var child : entity.getLicenses()) {
                
                child.setLicenseType(null); // retirer la référence inverse
                
            }
            entity.getLicenses().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}