package com.wut.directeur.data.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "employeePosition")
@Data
public class Position {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long minSalary;
    private String positionName;

    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;
}
