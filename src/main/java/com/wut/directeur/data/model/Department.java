package com.wut.directeur.data.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "department")
@Data
public class Department {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String departmentName;

    private String departmentDescription;

    @ManyToMany
    @JoinTable(name = "department_director",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> directors = new ArrayList<>();
}
