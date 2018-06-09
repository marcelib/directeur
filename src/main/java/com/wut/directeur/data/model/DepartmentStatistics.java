package com.wut.directeur.data.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "department_statistics")
@Data
public class DepartmentStatistics {

    @Id
    @Column(name = "name")
    private String name;

    private Long count;

    private BigDecimal expenses;
}
