package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Medication;
import com.example.modules.healthcare_management.repository.MedicationRepository;
import com.example.modules.healthcare_management.model.Prescription;
import com.example.modules.healthcare_management.repository.PrescriptionRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MedicationService extends BaseService<Medication> {

    protected final MedicationRepository medicationRepository;
    private final PrescriptionRepository prescriptionsRepository;

    public MedicationService(MedicationRepository repository,PrescriptionRepository prescriptionsRepository)
    {
        super(repository);
        this.medicationRepository = repository;
        this.prescriptionsRepository = prescriptionsRepository;
    }

    @Override
    public Medication save(Medication medication) {

        return medicationRepository.save(medication);
    }


    public Medication update(Long id, Medication medicationRequest) {
        Medication existing = medicationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medication not found"));

    // Copier les champs simples
        existing.setName(medicationRequest.getName());
        existing.setType(medicationRequest.getType());
        existing.setManufacturer(medicationRequest.getManufacturer());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (medicationRequest.getPrescriptions() != null) {
            existing.getPrescriptions().clear();
            List<Prescription> prescriptionsList = medicationRequest.getPrescriptions().stream()
                .map(item -> prescriptionsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Prescription not found")))
                .collect(Collectors.toList());
        existing.getPrescriptions().addAll(prescriptionsList);
        }

// Relations OneToMany : synchronisation sécurisée

        return medicationRepository.save(existing);
    }
}