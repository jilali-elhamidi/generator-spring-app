package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.MedicalRecord;
import com.example.modules.healthcare_management.repository.MedicalRecordRepository;

import com.example.modules.healthcare_management.model.Patient;
import com.example.modules.healthcare_management.repository.PatientRepository;

import com.example.modules.healthcare_management.model.Doctor;
import com.example.modules.healthcare_management.repository.DoctorRepository;

import com.example.modules.healthcare_management.model.MedicalFile;
import com.example.modules.healthcare_management.repository.MedicalFileRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService extends BaseService<MedicalRecord> {

    protected final MedicalRecordRepository medicalrecordRepository;
    
    protected final PatientRepository patientRepository;
    
    protected final DoctorRepository doctorRepository;
    
    protected final MedicalFileRepository attachmentsRepository;
    

    public MedicalRecordService(MedicalRecordRepository repository, PatientRepository patientRepository, DoctorRepository doctorRepository, MedicalFileRepository attachmentsRepository)
    {
        super(repository);
        this.medicalrecordRepository = repository;
        
        this.patientRepository = patientRepository;
        
        this.doctorRepository = doctorRepository;
        
        this.attachmentsRepository = attachmentsRepository;
        
    }

    @Transactional
    @Override
    public MedicalRecord save(MedicalRecord medicalrecord) {
    // ---------- OneToMany ----------
        if (medicalrecord.getAttachments() != null) {
            List<MedicalFile> managedAttachments = new ArrayList<>();
            for (MedicalFile item : medicalrecord.getAttachments()) {
                if (item.getId() != null) {
                    MedicalFile existingItem = attachmentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MedicalFile not found"));

                     existingItem.setRecord(medicalrecord);
                     managedAttachments.add(existingItem);
                } else {
                    item.setRecord(medicalrecord);
                    managedAttachments.add(item);
                }
            }
            medicalrecord.setAttachments(managedAttachments);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (medicalrecord.getPatient() != null) {
            if (medicalrecord.getPatient().getId() != null) {
                Patient existingPatient = patientRepository.findById(
                    medicalrecord.getPatient().getId()
                ).orElseThrow(() -> new RuntimeException("Patient not found with id "
                    + medicalrecord.getPatient().getId()));
                medicalrecord.setPatient(existingPatient);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Patient newPatient = patientRepository.save(medicalrecord.getPatient());
                medicalrecord.setPatient(newPatient);
            }
        }
        
        if (medicalrecord.getDoctor() != null) {
            if (medicalrecord.getDoctor().getId() != null) {
                Doctor existingDoctor = doctorRepository.findById(
                    medicalrecord.getDoctor().getId()
                ).orElseThrow(() -> new RuntimeException("Doctor not found with id "
                    + medicalrecord.getDoctor().getId()));
                medicalrecord.setDoctor(existingDoctor);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Doctor newDoctor = doctorRepository.save(medicalrecord.getDoctor());
                medicalrecord.setDoctor(newDoctor);
            }
        }
        
    // ---------- OneToOne ----------
    return medicalrecordRepository.save(medicalrecord);
}

    @Transactional
    @Override
    public MedicalRecord update(Long id, MedicalRecord medicalrecordRequest) {
        MedicalRecord existing = medicalrecordRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MedicalRecord not found"));

    // Copier les champs simples
        existing.setRecordDate(medicalrecordRequest.getRecordDate());
        existing.setDiagnosis(medicalrecordRequest.getDiagnosis());
        existing.setNotes(medicalrecordRequest.getNotes());

    // ---------- Relations ManyToOne ----------
        if (medicalrecordRequest.getPatient() != null &&
            medicalrecordRequest.getPatient().getId() != null) {

            Patient existingPatient = patientRepository.findById(
                medicalrecordRequest.getPatient().getId()
            ).orElseThrow(() -> new RuntimeException("Patient not found"));

            existing.setPatient(existingPatient);
        } else {
            existing.setPatient(null);
        }
        
        if (medicalrecordRequest.getDoctor() != null &&
            medicalrecordRequest.getDoctor().getId() != null) {

            Doctor existingDoctor = doctorRepository.findById(
                medicalrecordRequest.getDoctor().getId()
            ).orElseThrow(() -> new RuntimeException("Doctor not found"));

            existing.setDoctor(existingDoctor);
        } else {
            existing.setDoctor(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getAttachments().clear();

        if (medicalrecordRequest.getAttachments() != null) {
            for (var item : medicalrecordRequest.getAttachments()) {
                MedicalFile existingItem;
                if (item.getId() != null) {
                    existingItem = attachmentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MedicalFile not found"));
                } else {
                existingItem = item;
                }

                existingItem.setRecord(existing);
                existing.getAttachments().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return medicalrecordRepository.save(existing);
}

    // Pagination simple
    public Page<MedicalRecord> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MedicalRecord> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MedicalRecord.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MedicalRecord> saveAll(List<MedicalRecord> medicalrecordList) {
        return super.saveAll(medicalrecordList);
    }

}