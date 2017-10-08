package com.braintri.directeur.data;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="positionId")
    public Position position;

    public String name;
    public String surname;
    public String email;
}
