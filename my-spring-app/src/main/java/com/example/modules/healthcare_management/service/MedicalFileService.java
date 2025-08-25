package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.MedicalFile;
import com.example.modules.healthcare_management.repository.MedicalFileRepository;
import com.example.modules.healthcare_management.model.MedicalRecord;
import com.example.modules.healthcare_management.repository.MedicalRecordRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MedicalFileService extends BaseService<MedicalFile> {

    protected final MedicalFileRepository medicalfileRepository;
    private final MedicalRecordRepository recordRepository;

    public MedicalFileService(MedicalFileRepository repository, MedicalRecordRepository recordRepository)
    {
        super(repository);
        this.medicalfileRepository = repository;
        this.recordRepository = recordRepository;
    }

    @Override
    public MedicalFile save(MedicalFile medicalfile) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (medicalfile.getRecord() != null) {
            if (medicalfile.getRecord().getId() != null) {
                MedicalRecord existingRecord = recordRepository.findById(
                    medicalfile.getRecord().getId()
                ).orElseThrow(() -> new RuntimeException("MedicalRecord not found with id "
                    + medicalfile.getRecord().getId()));
                medicalfile.setRecord(existingRecord);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                MedicalRecord newRecord = recordRepository.save(medicalfile.getRecord());
                medicalfile.setRecord(newRecord);
            }
        }
        
    // ---------- OneToOne ----------
    return medicalfileRepository.save(medicalfile);
}


    public MedicalFile update(Long id, MedicalFile medicalfileRequest) {
        MedicalFile existing = medicalfileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MedicalFile not found"));

    // Copier les champs simples
        existing.setFileUrl(medicalfileRequest.getFileUrl());
        existing.setFileType(medicalfileRequest.getFileType());

    // ---------- Relations ManyToOne ----------
        if (medicalfileRequest.getRecord() != null &&
            medicalfileRequest.getRecord().getId() != null) {

            MedicalRecord existingRecord = recordRepository.findById(
                medicalfileRequest.getRecord().getId()
            ).orElseThrow(() -> new RuntimeException("MedicalRecord not found"));

            existing.setRecord(existingRecord);
        } else {
            existing.setRecord(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return medicalfileRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<MedicalFile> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        MedicalFile entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getRecord() != null) {
            entity.setRecord(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<MedicalFile> saveAll(List<MedicalFile> medicalfileList) {

        return medicalfileRepository.saveAll(medicalfileList);
    }

}