package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Doctor;
import com.example.modules.healthcare_management.repository.DoctorRepository;

import com.example.modules.healthcare_management.model.Appointment;
import com.example.modules.healthcare_management.repository.AppointmentRepository;

import com.example.modules.healthcare_management.model.Prescription;
import com.example.modules.healthcare_management.repository.PrescriptionRepository;

import com.example.modules.healthcare_management.model.Department;
import com.example.modules.healthcare_management.repository.DepartmentRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class DoctorService extends BaseService<Doctor> {

    protected final DoctorRepository doctorRepository;
    
    protected final AppointmentRepository appointmentsRepository;
    
    protected final PrescriptionRepository prescriptionsRepository;
    
    protected final DepartmentRepository departmentRepository;
    

    public DoctorService(DoctorRepository repository, AppointmentRepository appointmentsRepository, PrescriptionRepository prescriptionsRepository, DepartmentRepository departmentRepository)
    {
        super(repository);
        this.doctorRepository = repository;
        
        this.appointmentsRepository = appointmentsRepository;
        
        this.prescriptionsRepository = prescriptionsRepository;
        
        this.departmentRepository = departmentRepository;
        
    }

    @Transactional
    @Override
    public Doctor save(Doctor doctor) {
    // ---------- OneToMany ----------
        if (doctor.getAppointments() != null) {
            List<Appointment> managedAppointments = new ArrayList<>();
            for (Appointment item : doctor.getAppointments()) {
                if (item.getId() != null) {
                    Appointment existingItem = appointmentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Appointment not found"));

                     existingItem.setDoctor(doctor);
                     managedAppointments.add(existingItem);
                } else {
                    item.setDoctor(doctor);
                    managedAppointments.add(item);
                }
            }
            doctor.setAppointments(managedAppointments);
        }
    
        if (doctor.getPrescriptions() != null) {
            List<Prescription> managedPrescriptions = new ArrayList<>();
            for (Prescription item : doctor.getPrescriptions()) {
                if (item.getId() != null) {
                    Prescription existingItem = prescriptionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Prescription not found"));

                     existingItem.setDoctor(doctor);
                     managedPrescriptions.add(existingItem);
                } else {
                    item.setDoctor(doctor);
                    managedPrescriptions.add(item);
                }
            }
            doctor.setPrescriptions(managedPrescriptions);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (doctor.getDepartment() != null) {
            if (doctor.getDepartment().getId() != null) {
                Department existingDepartment = departmentRepository.findById(
                    doctor.getDepartment().getId()
                ).orElseThrow(() -> new RuntimeException("Department not found with id "
                    + doctor.getDepartment().getId()));
                doctor.setDepartment(existingDepartment);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Department newDepartment = departmentRepository.save(doctor.getDepartment());
                doctor.setDepartment(newDepartment);
            }
        }
        
    // ---------- OneToOne ----------
    return doctorRepository.save(doctor);
}

    @Transactional
    @Override
    public Doctor update(Long id, Doctor doctorRequest) {
        Doctor existing = doctorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));

    // Copier les champs simples
        existing.setFirstName(doctorRequest.getFirstName());
        existing.setLastName(doctorRequest.getLastName());
        existing.setSpecialty(doctorRequest.getSpecialty());
        existing.setEmail(doctorRequest.getEmail());
        existing.setPhoneNumber(doctorRequest.getPhoneNumber());

    // ---------- Relations ManyToOne ----------
        if (doctorRequest.getDepartment() != null &&
            doctorRequest.getDepartment().getId() != null) {

            Department existingDepartment = departmentRepository.findById(
                doctorRequest.getDepartment().getId()
            ).orElseThrow(() -> new RuntimeException("Department not found"));

            existing.setDepartment(existingDepartment);
        } else {
            existing.setDepartment(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getAppointments().clear();

        if (doctorRequest.getAppointments() != null) {
            for (var item : doctorRequest.getAppointments()) {
                Appointment existingItem;
                if (item.getId() != null) {
                    existingItem = appointmentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Appointment not found"));
                } else {
                existingItem = item;
                }

                existingItem.setDoctor(existing);
                existing.getAppointments().add(existingItem);
            }
        }
        
        existing.getPrescriptions().clear();

        if (doctorRequest.getPrescriptions() != null) {
            for (var item : doctorRequest.getPrescriptions()) {
                Prescription existingItem;
                if (item.getId() != null) {
                    existingItem = prescriptionsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Prescription not found"));
                } else {
                existingItem = item;
                }

                existingItem.setDoctor(existing);
                existing.getPrescriptions().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return doctorRepository.save(existing);
}

    // Pagination simple
    public Page<Doctor> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Doctor> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Doctor.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Doctor> saveAll(List<Doctor> doctorList) {
        return super.saveAll(doctorList);
    }

}