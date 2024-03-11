package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Role_sequence")
//    @SequenceGenerator(name = "Role_sequence", sequenceName = "Role_seq", initialValue = 1, allocationSize = 1)
    private int id;
    @Column
    private String name;
}
