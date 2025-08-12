package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Doctor;
import com.example.modules.healthcare_management.repository.DoctorRepository;
import com.example.modules.healthcare_management.model.Appointment;
import com.example.modules.healthcare_management.model.Prescription;
import com.example.modules.healthcare_management.model.Department;
import com.example.modules.healthcare_management.repository.DepartmentRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class DoctorService extends BaseService<Doctor> {

    protected final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;

    public DoctorService(DoctorRepository repository,DepartmentRepository departmentRepository)
    {
        super(repository);
        this.doctorRepository = repository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Doctor save(Doctor doctor) {

        if (doctor.getDepartment() != null && doctor.getDepartment().getId() != null) {
        Department department = departmentRepository.findById(doctor.getDepartment().getId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        doctor.setDepartment(department);
        }

        if (doctor.getAppointments() != null) {
            for (Appointment item : doctor.getAppointments()) {
            item.setDoctor(doctor);
            }
        }

        if (doctor.getPrescriptions() != null) {
            for (Prescription item : doctor.getPrescriptions()) {
            item.setDoctor(doctor);
            }
        }

        return doctorRepository.save(doctor);
    }


    public Doctor update(Long id, Doctor doctorRequest) {
        Doctor existing = doctorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));

    // Copier les champs simples
        existing.setFirstName(doctorRequest.getFirstName());
        existing.setLastName(doctorRequest.getLastName());
        existing.setSpecialty(doctorRequest.getSpecialty());
        existing.setEmail(doctorRequest.getEmail());
        existing.setPhoneNumber(doctorRequest.getPhoneNumber());

// Relations ManyToOne : mise à jour conditionnelle

        if (doctorRequest.getDepartment() != null && doctorRequest.getDepartment().getId() != null) {
        Department department = departmentRepository.findById(doctorRequest.getDepartment().getId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        existing.setDepartment(department);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getAppointments().clear();
        if (doctorRequest.getAppointments() != null) {
            for (var item : doctorRequest.getAppointments()) {
            item.setDoctor(existing);
            existing.getAppointments().add(item);
            }
        }

        existing.getPrescriptions().clear();
        if (doctorRequest.getPrescriptions() != null) {
            for (var item : doctorRequest.getPrescriptions()) {
            item.setDoctor(existing);
            existing.getPrescriptions().add(item);
            }
        }

        return doctorRepository.save(existing);
    }
}