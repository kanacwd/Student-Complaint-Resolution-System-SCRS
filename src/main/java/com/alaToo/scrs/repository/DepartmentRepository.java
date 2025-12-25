package com.alaToo.scrs.repository;

import com.alaToo.scrs.entity.Department;
import com.alaToo.scrs.entity.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);
    boolean existsByName(String name);
    List<Department> findByType(DepartmentType type);

    List<Department> findByActiveTrue();

    List<Department> findByTypeAndActiveTrue(DepartmentType type);
}