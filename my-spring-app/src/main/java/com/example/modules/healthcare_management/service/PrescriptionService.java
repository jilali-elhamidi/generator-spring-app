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
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class PrescriptionService extends BaseService<Prescription> {

    protected final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final MedicationRepository medicationsRepository;

    public PrescriptionService(PrescriptionRepository repository, PatientRepository patientRepository, DoctorRepository doctorRepository, MedicationRepository medicationsRepository)
    {
        super(repository);
        this.prescriptionRepository = repository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.medicationsRepository = medicationsRepository;
    }

    @Override
    public Prescription save(Prescription prescription) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (prescription.getMedications() != null &&
            !prescription.getMedications().isEmpty()) {

            List<Medication> attachedMedications = new ArrayList<>();
            for (Medication item : prescription.getMedications()) {
                if (item.getId() != null) {
                    Medication existingItem = medicationsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Medication not found with id " + item.getId()));
                    attachedMedications.add(existingItem);
                } else {

                    Medication newItem = medicationsRepository.save(item);
                    attachedMedications.add(newItem);
                }
            }

            prescription.setMedications(attachedMedications);

            // côté propriétaire (Medication → Prescription)
            attachedMedications.forEach(it -> it.getPrescriptions().add(prescription));
        }
        
    // ---------- ManyToOne ----------
        if (prescription.getPatient() != null) {
            if (prescription.getPatient().getId() != null) {
                Patient existingPatient = patientRepository.findById(
                    prescription.getPatient().getId()
                ).orElseThrow(() -> new RuntimeException("Patient not found with id "
                    + prescription.getPatient().getId()));
                prescription.setPatient(existingPatient);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Patient newPatient = patientRepository.save(prescription.getPatient());
                prescription.setPatient(newPatient);
            }
        }
        
        if (prescription.getDoctor() != null) {
            if (prescription.getDoctor().getId() != null) {
                Doctor existingDoctor = doctorRepository.findById(
                    prescription.getDoctor().getId()
                ).orElseThrow(() -> new RuntimeException("Doctor not found with id "
                    + prescription.getDoctor().getId()));
                prescription.setDoctor(existingDoctor);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Doctor newDoctor = doctorRepository.save(prescription.getDoctor());
                prescription.setDoctor(newDoctor);
            }
        }
        
    // ---------- OneToOne ----------
    return prescriptionRepository.save(prescription);
}


    public Prescription update(Long id, Prescription prescriptionRequest) {
        Prescription existing = prescriptionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Prescription not found"));

    // Copier les champs simples
        existing.setPrescriptionDate(prescriptionRequest.getPrescriptionDate());
        existing.setDosageInstructions(prescriptionRequest.getDosageInstructions());

    // ---------- Relations ManyToOne ----------
        if (prescriptionRequest.getPatient() != null &&
            prescriptionRequest.getPatient().getId() != null) {

            Patient existingPatient = patientRepository.findById(
                prescriptionRequest.getPatient().getId()
            ).orElseThrow(() -> new RuntimeException("Patient not found"));

            existing.setPatient(existingPatient);
        } else {
            existing.setPatient(null);
        }
        
        if (prescriptionRequest.getDoctor() != null &&
            prescriptionRequest.getDoctor().getId() != null) {

            Doctor existingDoctor = doctorRepository.findById(
                prescriptionRequest.getDoctor().getId()
            ).orElseThrow(() -> new RuntimeException("Doctor not found"));

            existing.setDoctor(existingDoctor);
        } else {
            existing.setDoctor(null);
        }
        
    // ---------- Relations ManyToOne ----------
        if (prescriptionRequest.getMedications() != null) {
            existing.getMedications().clear();

            List<Medication> medicationsList = prescriptionRequest.getMedications().stream()
                .map(item -> medicationsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Medication not found")))
                .collect(Collectors.toList());

            existing.getMedications().addAll(medicationsList);

            // Mettre à jour le côté inverse
            medicationsList.forEach(it -> {
                if (!it.getPrescriptions().contains(existing)) {
                    it.getPrescriptions().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return prescriptionRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Prescription> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Prescription entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
        if (entity.getMedications() != null) {
            for (Medication item : new ArrayList<>(entity.getMedications())) {
                
                item.getPrescriptions().remove(entity); // retire côté inverse
            }
            entity.getMedications().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getPatient() != null) {
            entity.setPatient(null);
        }
        
        if (entity.getDoctor() != null) {
            entity.setDoctor(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Prescription> saveAll(List<Prescription> prescriptionList) {

        return prescriptionRepository.saveAll(prescriptionList);
    }

}