package com.alaToo.scrs.service;

import com.alaToo.scrs.entity.Department;
import com.alaToo.scrs.entity.DepartmentType;
import com.alaToo.scrs.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        department.setName(departmentDetails.getName());
        department.setDescription(departmentDetails.getDescription());
        department.setType(departmentDetails.getType());
        department.setHeadOfDepartment(departmentDetails.getHeadOfDepartment());
        department.setEmail(departmentDetails.getEmail());
        department.setPhone(departmentDetails.getPhone());
        department.setActive(departmentDetails.isActive()); // Lombok сделает этот метод для boolean

        return departmentRepository.save(department);
    }

    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }

    public Optional<Department> findByName(String name) {
        return departmentRepository.findByName(name);
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public List<Department> findByType(DepartmentType type) {
        return departmentRepository.findByType(type);
    }

    public List<Department> findActiveDepartments() {
        return departmentRepository.findByActiveTrue();
    }

    public List<Department> findActiveByType(DepartmentType type) {
        return departmentRepository.findByTypeAndActiveTrue(type);
    }

    public boolean existsByName(String name) {
        return departmentRepository.existsByName(name);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}