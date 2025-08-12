package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.MedicalRecord;
import com.example.modules.healthcare_management.repository.MedicalRecordRepository;
import com.example.modules.healthcare_management.model.Patient;
import com.example.modules.healthcare_management.repository.PatientRepository;
import com.example.modules.healthcare_management.model.Doctor;
import com.example.modules.healthcare_management.repository.DoctorRepository;
import com.example.modules.healthcare_management.model.MedicalFile;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MedicalRecordService extends BaseService<MedicalRecord> {

    protected final MedicalRecordRepository medicalrecordRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public MedicalRecordService(MedicalRecordRepository repository,PatientRepository patientRepository,DoctorRepository doctorRepository)
    {
        super(repository);
        this.medicalrecordRepository = repository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public MedicalRecord save(MedicalRecord medicalrecord) {

        if (medicalrecord.getPatient() != null && medicalrecord.getPatient().getId() != null) {
        Patient patient = patientRepository.findById(medicalrecord.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        medicalrecord.setPatient(patient);
        }

        if (medicalrecord.getDoctor() != null && medicalrecord.getDoctor().getId() != null) {
        Doctor doctor = doctorRepository.findById(medicalrecord.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        medicalrecord.setDoctor(doctor);
        }

        if (medicalrecord.getAttachments() != null) {
            for (MedicalFile item : medicalrecord.getAttachments()) {
            item.setRecord(medicalrecord);
            }
        }

        return medicalrecordRepository.save(medicalrecord);
    }


    public MedicalRecord update(Long id, MedicalRecord medicalrecordRequest) {
        MedicalRecord existing = medicalrecordRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MedicalRecord not found"));

    // Copier les champs simples
        existing.setRecordDate(medicalrecordRequest.getRecordDate());
        existing.setDiagnosis(medicalrecordRequest.getDiagnosis());
        existing.setNotes(medicalrecordRequest.getNotes());

// Relations ManyToOne : mise à jour conditionnelle

        if (medicalrecordRequest.getPatient() != null && medicalrecordRequest.getPatient().getId() != null) {
        Patient patient = patientRepository.findById(medicalrecordRequest.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        existing.setPatient(patient);
        }

        if (medicalrecordRequest.getDoctor() != null && medicalrecordRequest.getDoctor().getId() != null) {
        Doctor doctor = doctorRepository.findById(medicalrecordRequest.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        existing.setDoctor(doctor);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getAttachments().clear();
        if (medicalrecordRequest.getAttachments() != null) {
            for (var item : medicalrecordRequest.getAttachments()) {
            item.setRecord(existing);
            existing.getAttachments().add(item);
            }
        }

        return medicalrecordRepository.save(existing);
    }
}