package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Patient;
import com.example.modules.healthcare_management.repository.PatientRepository;
import com.example.modules.healthcare_management.model.MedicalRecord;
import com.example.modules.healthcare_management.model.Appointment;
import com.example.modules.healthcare_management.model.Prescription;
import com.example.modules.healthcare_management.model.Invoice;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class PatientService extends BaseService<Patient> {

    protected final PatientRepository patientRepository;

    public PatientService(PatientRepository repository)
    {
        super(repository);
        this.patientRepository = repository;
    }

    @Override
    public Patient save(Patient patient) {

        if (patient.getMedicalRecords() != null) {
            for (MedicalRecord item : patient.getMedicalRecords()) {
            item.setPatient(patient);
            }
        }

        if (patient.getAppointments() != null) {
            for (Appointment item : patient.getAppointments()) {
            item.setPatient(patient);
            }
        }

        if (patient.getPrescriptions() != null) {
            for (Prescription item : patient.getPrescriptions()) {
            item.setPatient(patient);
            }
        }

        if (patient.getInvoices() != null) {
            for (Invoice item : patient.getInvoices()) {
            item.setPatient(patient);
            }
        }

        return patientRepository.save(patient);
    }


    public Patient update(Long id, Patient patientRequest) {
        Patient existing = patientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Patient not found"));

    // Copier les champs simples
        existing.setFirstName(patientRequest.getFirstName());
        existing.setLastName(patientRequest.getLastName());
        existing.setDateOfBirth(patientRequest.getDateOfBirth());
        existing.setGender(patientRequest.getGender());
        existing.setEmail(patientRequest.getEmail());
        existing.setPhoneNumber(patientRequest.getPhoneNumber());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getMedicalRecords().clear();
        if (patientRequest.getMedicalRecords() != null) {
            for (var item : patientRequest.getMedicalRecords()) {
            item.setPatient(existing);
            existing.getMedicalRecords().add(item);
            }
        }

        existing.getAppointments().clear();
        if (patientRequest.getAppointments() != null) {
            for (var item : patientRequest.getAppointments()) {
            item.setPatient(existing);
            existing.getAppointments().add(item);
            }
        }

        existing.getPrescriptions().clear();
        if (patientRequest.getPrescriptions() != null) {
            for (var item : patientRequest.getPrescriptions()) {
            item.setPatient(existing);
            existing.getPrescriptions().add(item);
            }
        }

        existing.getInvoices().clear();
        if (patientRequest.getInvoices() != null) {
            for (var item : patientRequest.getInvoices()) {
            item.setPatient(existing);
            existing.getInvoices().add(item);
            }
        }

        return patientRepository.save(existing);
    }
}