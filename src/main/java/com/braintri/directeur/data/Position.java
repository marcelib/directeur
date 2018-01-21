package com.braintri.directeur.data;

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
    private Long min_salary;
    private Long department_id;
    private Long role_id;
    private String position_name;
}
