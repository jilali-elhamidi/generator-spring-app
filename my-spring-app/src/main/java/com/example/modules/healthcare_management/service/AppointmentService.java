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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class AppointmentService extends BaseService<Appointment> {

    protected final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final RoomRepository roomRepository;

    public AppointmentService(AppointmentRepository repository, PatientRepository patientRepository, DoctorRepository doctorRepository, RoomRepository roomRepository)
    {
        super(repository);
        this.appointmentRepository = repository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Appointment save(Appointment appointment) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (appointment.getPatient() != null &&
            appointment.getPatient().getId() != null) {

            Patient existingPatient = patientRepository.findById(
                appointment.getPatient().getId()
            ).orElseThrow(() -> new RuntimeException("Patient not found"));

            appointment.setPatient(existingPatient);
        }
        
        if (appointment.getDoctor() != null &&
            appointment.getDoctor().getId() != null) {

            Doctor existingDoctor = doctorRepository.findById(
                appointment.getDoctor().getId()
            ).orElseThrow(() -> new RuntimeException("Doctor not found"));

            appointment.setDoctor(existingDoctor);
        }
        
        if (appointment.getRoom() != null &&
            appointment.getRoom().getId() != null) {

            Room existingRoom = roomRepository.findById(
                appointment.getRoom().getId()
            ).orElseThrow(() -> new RuntimeException("Room not found"));

            appointment.setRoom(existingRoom);
        }
        
    // ---------- OneToOne ----------
    return appointmentRepository.save(appointment);
}


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
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return appointmentRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Appointment> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Appointment entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getPatient() != null) {
            entity.setPatient(null);
        }
        
        if (entity.getDoctor() != null) {
            entity.setDoctor(null);
        }
        
        if (entity.getRoom() != null) {
            entity.setRoom(null);
        }
        
        repository.delete(entity);
        return true;
    }
}