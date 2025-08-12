package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.MedicalFile;
import com.example.modules.healthcare_management.repository.MedicalFileRepository;
import com.example.modules.healthcare_management.model.MedicalRecord;
import com.example.modules.healthcare_management.repository.MedicalRecordRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MedicalFileService extends BaseService<MedicalFile> {

    protected final MedicalFileRepository medicalfileRepository;
    private final MedicalRecordRepository recordRepository;

    public MedicalFileService(MedicalFileRepository repository,MedicalRecordRepository recordRepository)
    {
        super(repository);
        this.medicalfileRepository = repository;
        this.recordRepository = recordRepository;
    }

    @Override
    public MedicalFile save(MedicalFile medicalfile) {

        if (medicalfile.getRecord() != null && medicalfile.getRecord().getId() != null) {
        MedicalRecord record = recordRepository.findById(medicalfile.getRecord().getId())
                .orElseThrow(() -> new RuntimeException("MedicalRecord not found"));
        medicalfile.setRecord(record);
        }

        return medicalfileRepository.save(medicalfile);
    }


    public MedicalFile update(Long id, MedicalFile medicalfileRequest) {
        MedicalFile existing = medicalfileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MedicalFile not found"));

    // Copier les champs simples
        existing.setFileUrl(medicalfileRequest.getFileUrl());
        existing.setFileType(medicalfileRequest.getFileType());

// Relations ManyToOne : mise à jour conditionnelle

        if (medicalfileRequest.getRecord() != null && medicalfileRequest.getRecord().getId() != null) {
        MedicalRecord record = recordRepository.findById(medicalfileRequest.getRecord().getId())
                .orElseThrow(() -> new RuntimeException("MedicalRecord not found"));
        existing.setRecord(record);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        return medicalfileRepository.save(existing);
    }
}