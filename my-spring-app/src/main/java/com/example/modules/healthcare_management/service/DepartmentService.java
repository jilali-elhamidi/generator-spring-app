package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Department;
import com.example.modules.healthcare_management.repository.DepartmentRepository;
import com.example.modules.healthcare_management.model.Doctor;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class DepartmentService extends BaseService<Department> {

    protected final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository repository)
    {
        super(repository);
        this.departmentRepository = repository;
    }

    @Override
    public Department save(Department department) {

        if (department.getDoctors() != null) {
            for (Doctor item : department.getDoctors()) {
            item.setDepartment(department);
            }
        }
        if (department.getHead() != null) {
        department.getHead().setDepartment(department);
        }

        return departmentRepository.save(department);
    }


    public Department update(Long id, Department departmentRequest) {
        Department existing = departmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Department not found"));

    // Copier les champs simples
        existing.setName(departmentRequest.getName());
        existing.setDescription(departmentRequest.getDescription());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getDoctors().clear();
        if (departmentRequest.getDoctors() != null) {
            for (var item : departmentRequest.getDoctors()) {
            item.setDepartment(existing);
            existing.getDoctors().add(item);
            }
        }

        return departmentRepository.save(existing);
    }
}