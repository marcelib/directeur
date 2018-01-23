package com.braintri.directeur.data;

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
}
