package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Department;
import com.example.modules.healthcare_management.repository.DepartmentRepository;

import com.example.modules.healthcare_management.model.Doctor;
import com.example.modules.healthcare_management.repository.DoctorRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class DepartmentService extends BaseService<Department> {

    protected final DepartmentRepository departmentRepository;
    
    protected final DoctorRepository doctorsRepository;
    

    public DepartmentService(DepartmentRepository repository, DoctorRepository doctorsRepository)
    {
        super(repository);
        this.departmentRepository = repository;
        
        this.doctorsRepository = doctorsRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public Department update(Long id, Department departmentRequest) {
        Department existing = departmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Department not found"));

    // Copier les champs simples
        existing.setName(departmentRequest.getName());
        existing.setDescription(departmentRequest.getDescription());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<Department> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Department> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Department.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Department> saveAll(List<Department> departmentList) {
        return super.saveAll(departmentList);
    }

}