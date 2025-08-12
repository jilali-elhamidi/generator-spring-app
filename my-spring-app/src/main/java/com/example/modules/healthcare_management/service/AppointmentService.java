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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class AppointmentService extends BaseService<Appointment> {

    protected final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final RoomRepository roomRepository;

    public AppointmentService(AppointmentRepository repository,PatientRepository patientRepository,DoctorRepository doctorRepository,RoomRepository roomRepository)
    {
        super(repository);
        this.appointmentRepository = repository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Appointment save(Appointment appointment) {

        if (appointment.getPatient() != null && appointment.getPatient().getId() != null) {
        Patient patient = patientRepository.findById(appointment.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        appointment.setPatient(patient);
        }

        if (appointment.getDoctor() != null && appointment.getDoctor().getId() != null) {
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        appointment.setDoctor(doctor);
        }

        if (appointment.getRoom() != null && appointment.getRoom().getId() != null) {
        Room room = roomRepository.findById(appointment.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        appointment.setRoom(room);
        }

        return appointmentRepository.save(appointment);
    }


    public Appointment update(Long id, Appointment appointmentRequest) {
        Appointment existing = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));

    // Copier les champs simples
        existing.setAppointmentDate(appointmentRequest.getAppointmentDate());
        existing.setStatus(appointmentRequest.getStatus());

// Relations ManyToOne : mise à jour conditionnelle

        if (appointmentRequest.getPatient() != null && appointmentRequest.getPatient().getId() != null) {
        Patient patient = patientRepository.findById(appointmentRequest.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        existing.setPatient(patient);
        }

        if (appointmentRequest.getDoctor() != null && appointmentRequest.getDoctor().getId() != null) {
        Doctor doctor = doctorRepository.findById(appointmentRequest.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        existing.setDoctor(doctor);
        }

        if (appointmentRequest.getRoom() != null && appointmentRequest.getRoom().getId() != null) {
        Room room = roomRepository.findById(appointmentRequest.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        existing.setRoom(room);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        return appointmentRepository.save(existing);
    }
}