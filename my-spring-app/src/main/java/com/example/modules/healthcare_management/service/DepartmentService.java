package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Department;
import com.example.modules.healthcare_management.repository.DepartmentRepository;
import com.example.modules.healthcare_management.model.Doctor;
import com.example.modules.healthcare_management.repository.DoctorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class DepartmentService extends BaseService<Department> {

    protected final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorsRepository;

    public DepartmentService(DepartmentRepository repository, DoctorRepository doctorsRepository)
    {
        super(repository);
        this.departmentRepository = repository;
        this.doctorsRepository = doctorsRepository;
    }

    @Override
    public Department save(Department department) {
    // ---------- OneToMany ----------
        if (department.getDoctors() != null) {
            List<Doctor> managedDoctors = new ArrayList<>();
            for (Doctor item : department.getDoctors()) {
                if (item.getId() != null) {
                    Doctor existingItem = doctorsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Doctor not found"));

                     existingItem.setDepartment(department);
                     managedDoctors.add(existingItem);
                } else {
                    item.setDepartment(department);
                    managedDoctors.add(item);
                }
            }
            department.setDoctors(managedDoctors);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return departmentRepository.save(department);
}


    public Department update(Long id, Department departmentRequest) {
        Department existing = departmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Department not found"));

    // Copier les champs simples
        existing.setName(departmentRequest.getName());
        existing.setDescription(departmentRequest.getDescription());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getDoctors().clear();

        if (departmentRequest.getDoctors() != null) {
            for (var item : departmentRequest.getDoctors()) {
                Doctor existingItem;
                if (item.getId() != null) {
                    existingItem = doctorsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Doctor not found"));
                } else {
                existingItem = item;
                }

                existingItem.setDepartment(existing);
                existing.getDoctors().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return departmentRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Department> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Department entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getDoctors() != null) {
            for (var child : entity.getDoctors()) {
                // retirer la référence inverse
                child.setDepartment(null);
            }
            entity.getDoctors().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}