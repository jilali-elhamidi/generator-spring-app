package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Medication;
import com.example.modules.healthcare_management.repository.MedicationRepository;
import com.example.modules.healthcare_management.model.Prescription;
import com.example.modules.healthcare_management.repository.PrescriptionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MedicationService extends BaseService<Medication> {

    protected final MedicationRepository medicationRepository;
    private final PrescriptionRepository prescriptionsRepository;

    public MedicationService(MedicationRepository repository, PrescriptionRepository prescriptionsRepository)
    {
        super(repository);
        this.medicationRepository = repository;
        this.prescriptionsRepository = prescriptionsRepository;
    }

    @Override
    public Medication save(Medication medication) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (medication.getPrescriptions() != null &&
            !medication.getPrescriptions().isEmpty()) {

            List<Prescription> attachedPrescriptions = new ArrayList<>();
            for (Prescription item : medication.getPrescriptions()) {
                if (item.getId() != null) {
                    Prescription existingItem = prescriptionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Prescription not found with id " + item.getId()));
                    attachedPrescriptions.add(existingItem);
                } else {

                    Prescription newItem = prescriptionsRepository.save(item);
                    attachedPrescriptions.add(newItem);
                }
            }

            medication.setPrescriptions(attachedPrescriptions);

            // côté propriétaire (Prescription → Medication)
            attachedPrescriptions.forEach(it -> it.getMedications().add(medication));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return medicationRepository.save(medication);
}


    public Medication update(Long id, Medication medicationRequest) {
        Medication existing = medicationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medication not found"));

    // Copier les champs simples
        existing.setName(medicationRequest.getName());
        existing.setType(medicationRequest.getType());
        existing.setManufacturer(medicationRequest.getManufacturer());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
        if (medicationRequest.getPrescriptions() != null) {
            existing.getPrescriptions().clear();

            List<Prescription> prescriptionsList = medicationRequest.getPrescriptions().stream()
                .map(item -> prescriptionsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Prescription not found")))
                .collect(Collectors.toList());

            existing.getPrescriptions().addAll(prescriptionsList);

            // Mettre à jour le côté inverse
            prescriptionsList.forEach(it -> {
                if (!it.getMedications().contains(existing)) {
                    it.getMedications().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return medicationRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Medication> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Medication entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
        if (entity.getPrescriptions() != null) {
            for (Prescription item : new ArrayList<>(entity.getPrescriptions())) {
                
                item.getMedications().remove(entity); // retire côté inverse
            }
            entity.getPrescriptions().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Medication> saveAll(List<Medication> medicationList) {

        return medicationRepository.saveAll(medicationList);
    }

}