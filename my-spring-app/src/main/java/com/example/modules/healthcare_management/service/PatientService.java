package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Patient;
import com.example.modules.healthcare_management.repository.PatientRepository;
import com.example.modules.healthcare_management.model.MedicalRecord;
import com.example.modules.healthcare_management.repository.MedicalRecordRepository;
import com.example.modules.healthcare_management.model.Appointment;
import com.example.modules.healthcare_management.repository.AppointmentRepository;
import com.example.modules.healthcare_management.model.Prescription;
import com.example.modules.healthcare_management.repository.PrescriptionRepository;
import com.example.modules.healthcare_management.model.Invoice;
import com.example.modules.healthcare_management.repository.InvoiceRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class PatientService extends BaseService<Patient> {

    protected final PatientRepository patientRepository;
    private final MedicalRecordRepository medicalRecordsRepository;
    private final AppointmentRepository appointmentsRepository;
    private final PrescriptionRepository prescriptionsRepository;
    private final InvoiceRepository invoicesRepository;

    public PatientService(PatientRepository repository, MedicalRecordRepository medicalRecordsRepository, AppointmentRepository appointmentsRepository, PrescriptionRepository prescriptionsRepository, InvoiceRepository invoicesRepository)
    {
        super(repository);
        this.patientRepository = repository;
        this.medicalRecordsRepository = medicalRecordsRepository;
        this.appointmentsRepository = appointmentsRepository;
        this.prescriptionsRepository = prescriptionsRepository;
        this.invoicesRepository = invoicesRepository;
    }

    @Override
    public Patient save(Patient patient) {
    // ---------- OneToMany ----------
        if (patient.getMedicalRecords() != null) {
            List<MedicalRecord> managedMedicalRecords = new ArrayList<>();
            for (MedicalRecord item : patient.getMedicalRecords()) {
                if (item.getId() != null) {
                    MedicalRecord existingItem = medicalRecordsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MedicalRecord not found"));

                     existingItem.setPatient(patient);
                     managedMedicalRecords.add(existingItem);
                } else {
                    item.setPatient(patient);
                    managedMedicalRecords.add(item);
                }
            }
            patient.setMedicalRecords(managedMedicalRecords);
        }
    
        if (patient.getAppointments() != null) {
            List<Appointment> managedAppointments = new ArrayList<>();
            for (Appointment item : patient.getAppointments()) {
                if (item.getId() != null) {
                    Appointment existingItem = appointmentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Appointment not found"));

                     existingItem.setPatient(patient);
                     managedAppointments.add(existingItem);
                } else {
                    item.setPatient(patient);
                    managedAppointments.add(item);
                }
            }
            patient.setAppointments(managedAppointments);
        }
    
        if (patient.getPrescriptions() != null) {
            List<Prescription> managedPrescriptions = new ArrayList<>();
            for (Prescription item : patient.getPrescriptions()) {
                if (item.getId() != null) {
                    Prescription existingItem = prescriptionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Prescription not found"));

                     existingItem.setPatient(patient);
                     managedPrescriptions.add(existingItem);
                } else {
                    item.setPatient(patient);
                    managedPrescriptions.add(item);
                }
            }
            patient.setPrescriptions(managedPrescriptions);
        }
    
        if (patient.getInvoices() != null) {
            List<Invoice> managedInvoices = new ArrayList<>();
            for (Invoice item : patient.getInvoices()) {
                if (item.getId() != null) {
                    Invoice existingItem = invoicesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Invoice not found"));

                     existingItem.setPatient(patient);
                     managedInvoices.add(existingItem);
                } else {
                    item.setPatient(patient);
                    managedInvoices.add(item);
                }
            }
            patient.setInvoices(managedInvoices);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
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

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getMedicalRecords().clear();

        if (patientRequest.getMedicalRecords() != null) {
            for (var item : patientRequest.getMedicalRecords()) {
                MedicalRecord existingItem;
                if (item.getId() != null) {
                    existingItem = medicalRecordsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MedicalRecord not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPatient(existing);
                existing.getMedicalRecords().add(existingItem);
            }
        }
        
        existing.getAppointments().clear();

        if (patientRequest.getAppointments() != null) {
            for (var item : patientRequest.getAppointments()) {
                Appointment existingItem;
                if (item.getId() != null) {
                    existingItem = appointmentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Appointment not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPatient(existing);
                existing.getAppointments().add(existingItem);
            }
        }
        
        existing.getPrescriptions().clear();

        if (patientRequest.getPrescriptions() != null) {
            for (var item : patientRequest.getPrescriptions()) {
                Prescription existingItem;
                if (item.getId() != null) {
                    existingItem = prescriptionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Prescription not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPatient(existing);
                existing.getPrescriptions().add(existingItem);
            }
        }
        
        existing.getInvoices().clear();

        if (patientRequest.getInvoices() != null) {
            for (var item : patientRequest.getInvoices()) {
                Invoice existingItem;
                if (item.getId() != null) {
                    existingItem = invoicesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Invoice not found"));
                } else {
                existingItem = item;
                }

                existingItem.setPatient(existing);
                existing.getInvoices().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return patientRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Patient> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Patient entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getMedicalRecords() != null) {
            for (var child : entity.getMedicalRecords()) {
                // retirer la référence inverse
                child.setPatient(null);
            }
            entity.getMedicalRecords().clear();
        }
        
        if (entity.getAppointments() != null) {
            for (var child : entity.getAppointments()) {
                // retirer la référence inverse
                child.setPatient(null);
            }
            entity.getAppointments().clear();
        }
        
        if (entity.getPrescriptions() != null) {
            for (var child : entity.getPrescriptions()) {
                // retirer la référence inverse
                child.setPatient(null);
            }
            entity.getPrescriptions().clear();
        }
        
        if (entity.getInvoices() != null) {
            for (var child : entity.getInvoices()) {
                // retirer la référence inverse
                child.setPatient(null);
            }
            entity.getInvoices().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}