package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Appointment;
import com.example.modules.healthcare_management.repository.AppointmentRepository;

import com.example.modules.healthcare_management.model.Patient;
import com.example.modules.healthcare_management.repository.PatientRepository;

import com.example.modules.healthcare_management.model.Doctor;
import com.example.modules.healthcare_management.repository.DoctorRepository;

import com.example.modules.healthcare_management.model.Room;
import com.example.modules.healthcare_management.repository.RoomRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class AppointmentService extends BaseService<Appointment> {

    protected final AppointmentRepository appointmentRepository;
    
    protected final PatientRepository patientRepository;
    
    protected final DoctorRepository doctorRepository;
    
    protected final RoomRepository roomRepository;
    

    public AppointmentService(AppointmentRepository repository, PatientRepository patientRepository, DoctorRepository doctorRepository, RoomRepository roomRepository)
    {
        super(repository);
        this.appointmentRepository = repository;
        
        this.patientRepository = patientRepository;
        
        this.doctorRepository = doctorRepository;
        
        this.roomRepository = roomRepository;
        
    }

    @Transactional
    @Override
    public Appointment save(Appointment appointment) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (appointment.getPatient() != null) {
            if (appointment.getPatient().getId() != null) {
                Patient existingPatient = patientRepository.findById(
                    appointment.getPatient().getId()
                ).orElseThrow(() -> new RuntimeException("Patient not found with id "
                    + appointment.getPatient().getId()));
                appointment.setPatient(existingPatient);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Patient newPatient = patientRepository.save(appointment.getPatient());
                appointment.setPatient(newPatient);
            }
        }
        
        if (appointment.getDoctor() != null) {
            if (appointment.getDoctor().getId() != null) {
                Doctor existingDoctor = doctorRepository.findById(
                    appointment.getDoctor().getId()
                ).orElseThrow(() -> new RuntimeException("Doctor not found with id "
                    + appointment.getDoctor().getId()));
                appointment.setDoctor(existingDoctor);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Doctor newDoctor = doctorRepository.save(appointment.getDoctor());
                appointment.setDoctor(newDoctor);
            }
        }
        
        if (appointment.getRoom() != null) {
            if (appointment.getRoom().getId() != null) {
                Room existingRoom = roomRepository.findById(
                    appointment.getRoom().getId()
                ).orElseThrow(() -> new RuntimeException("Room not found with id "
                    + appointment.getRoom().getId()));
                appointment.setRoom(existingRoom);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Room newRoom = roomRepository.save(appointment.getRoom());
                appointment.setRoom(newRoom);
            }
        }
        
    // ---------- OneToOne ----------
    return appointmentRepository.save(appointment);
}

    @Transactional
    @Override
    public Appointment update(Long id, Appointment appointmentRequest) {
        Appointment existing = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));

    // Copier les champs simples
        existing.setAppointmentDate(appointmentRequest.getAppointmentDate());
        existing.setStatus(appointmentRequest.getStatus());

    // ---------- Relations ManyToOne ----------
        if (appointmentRequest.getPatient() != null &&
            appointmentRequest.getPatient().getId() != null) {

            Patient existingPatient = patientRepository.findById(
                appointmentRequest.getPatient().getId()
            ).orElseThrow(() -> new RuntimeException("Patient not found"));

            existing.setPatient(existingPatient);
        } else {
            existing.setPatient(null);
        }
        
        if (appointmentRequest.getDoctor() != null &&
            appointmentRequest.getDoctor().getId() != null) {

            Doctor existingDoctor = doctorRepository.findById(
                appointmentRequest.getDoctor().getId()
            ).orElseThrow(() -> new RuntimeException("Doctor not found"));

            existing.setDoctor(existingDoctor);
        } else {
            existing.setDoctor(null);
        }
        
        if (appointmentRequest.getRoom() != null &&
            appointmentRequest.getRoom().getId() != null) {

            Room existingRoom = roomRepository.findById(
                appointmentRequest.getRoom().getId()
            ).orElseThrow(() -> new RuntimeException("Room not found"));

            existing.setRoom(existingRoom);
        } else {
            existing.setRoom(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return appointmentRepository.save(existing);
}

    // Pagination simple
    public Page<Appointment> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Appointment> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Appointment.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Appointment> saveAll(List<Appointment> appointmentList) {
        return super.saveAll(appointmentList);
    }

}