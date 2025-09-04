package com.github.ots.proposal.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "patient")
@Getter
@Setter
@EqualsAndHashCode
public class Patient {

    @Id
    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Embedded
    private Address address;

    private String insuranceCompany;
    private LocalDate insuranceExpiryDate;

}
