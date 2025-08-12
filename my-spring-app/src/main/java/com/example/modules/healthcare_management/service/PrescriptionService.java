package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Prescription;
import com.example.modules.healthcare_management.repository.PrescriptionRepository;
import com.example.modules.healthcare_management.model.Patient;
import com.example.modules.healthcare_management.repository.PatientRepository;
import com.example.modules.healthcare_management.model.Doctor;
import com.example.modules.healthcare_management.repository.DoctorRepository;
import com.example.modules.healthcare_management.model.Medication;
import com.example.modules.healthcare_management.repository.MedicationRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class PrescriptionService extends BaseService<Prescription> {

    protected final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final MedicationRepository medicationsRepository;

    public PrescriptionService(PrescriptionRepository repository,PatientRepository patientRepository,DoctorRepository doctorRepository,MedicationRepository medicationsRepository)
    {
        super(repository);
        this.prescriptionRepository = repository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.medicationsRepository = medicationsRepository;
    }

    @Override
    public Prescription save(Prescription prescription) {

        if (prescription.getPatient() != null && prescription.getPatient().getId() != null) {
        Patient patient = patientRepository.findById(prescription.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        prescription.setPatient(patient);
        }

        if (prescription.getDoctor() != null && prescription.getDoctor().getId() != null) {
        Doctor doctor = doctorRepository.findById(prescription.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        prescription.setDoctor(doctor);
        }

        return prescriptionRepository.save(prescription);
    }


    public Prescription update(Long id, Prescription prescriptionRequest) {
        Prescription existing = prescriptionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Prescription not found"));

    // Copier les champs simples
        existing.setPrescriptionDate(prescriptionRequest.getPrescriptionDate());
        existing.setDosageInstructions(prescriptionRequest.getDosageInstructions());

// Relations ManyToOne : mise à jour conditionnelle

        if (prescriptionRequest.getPatient() != null && prescriptionRequest.getPatient().getId() != null) {
        Patient patient = patientRepository.findById(prescriptionRequest.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        existing.setPatient(patient);
        }

        if (prescriptionRequest.getDoctor() != null && prescriptionRequest.getDoctor().getId() != null) {
        Doctor doctor = doctorRepository.findById(prescriptionRequest.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        existing.setDoctor(doctor);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (prescriptionRequest.getMedications() != null) {
            existing.getMedications().clear();
            List<Medication> medicationsList = prescriptionRequest.getMedications().stream()
                .map(item -> medicationsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Medication not found")))
                .collect(Collectors.toList());
        existing.getMedications().addAll(medicationsList);
        }

// Relations OneToMany : synchronisation sécurisée

        return prescriptionRepository.save(existing);
    }
}