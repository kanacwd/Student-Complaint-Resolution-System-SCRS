package com.alaToo.scrs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private DepartmentType type;

    private String headOfDepartment;
    private String email;
    private String phone;
    private boolean active;

    public Department(String name, String description, DepartmentType type, String headOfDepartment, String email, String phone) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.headOfDepartment = headOfDepartment;
        this.email = email;
        this.phone = phone;
        this.active = true;
    }
}