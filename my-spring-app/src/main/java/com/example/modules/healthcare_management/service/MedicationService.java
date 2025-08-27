package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Medication;
import com.example.modules.healthcare_management.repository.MedicationRepository;

import com.example.modules.healthcare_management.model.Prescription;
import com.example.modules.healthcare_management.repository.PrescriptionRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MedicationService extends BaseService<Medication> {

    protected final MedicationRepository medicationRepository;
    
    protected final PrescriptionRepository prescriptionsRepository;
    

    public MedicationService(MedicationRepository repository, PrescriptionRepository prescriptionsRepository)
    {
        super(repository);
        this.medicationRepository = repository;
        
        this.prescriptionsRepository = prescriptionsRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public Medication update(Long id, Medication medicationRequest) {
        Medication existing = medicationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medication not found"));

    // Copier les champs simples
        existing.setName(medicationRequest.getName());
        existing.setType(medicationRequest.getType());
        existing.setManufacturer(medicationRequest.getManufacturer());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<Medication> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Medication> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Medication.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Medication> saveAll(List<Medication> medicationList) {
        return super.saveAll(medicationList);
    }

}