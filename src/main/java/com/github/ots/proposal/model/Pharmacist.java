package com.github.ots.proposal.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pharmacist")
@Getter
@Setter
public class Pharmacist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pharmacist_id")
    private Long id;

    @Column(name = "first_name")
    private String name;

}
