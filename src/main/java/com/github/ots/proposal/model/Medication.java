package com.github.ots.proposal.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "medication")
@EqualsAndHashCode(of = {"id","medicationName"})
@Getter
@Setter
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medication_name", unique = true, nullable = false)
    private String medicationName;

    @ManyToMany(mappedBy = "medications",cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    private Set<Prescription> prescriptions = new HashSet<>();
}
