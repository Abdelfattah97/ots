package com.github.ots.proposal.model;

import com.github.ots.common.audit.model.AbstractUpdateAudit;
import com.github.ots.proposal.enums.ProposalStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "prescription")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Prescription extends AbstractUpdateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescription_id")
    private Long prescriptionId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "prescription_date")
    private LocalDate prescriptionDate;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "prescription_medications",
            joinColumns = @JoinColumn(name = "prescription_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id"),
            foreignKey = @ForeignKey(name = "fk_prescription_medications_prescription"),
            inverseForeignKey = @ForeignKey(name = "fk_prescription_medications_medication")
    )
    private Set<Medication> medications = new HashSet<>();

    @Column(name = "avg_med_finish_date")
    private LocalDate avgMedFinishDate;

    @Column(name = "clinic_name")
    private String clinicName;

    @Column(name = "calling_date")
    private LocalDate callingDate;

    private ProposalStatus proposalStatus;

    @ManyToOne
    @JoinColumn(name = "pharmacist_id")
    private Pharmacist pharmacist;

    @ElementCollection
    @CollectionTable(name = "prescription_appointments",
            joinColumns = @JoinColumn(name = "prescription_id",
                    foreignKey = @ForeignKey(name = "fk_appointment_prescription"))
    )
    private Set<String> appointments = new HashSet<>();

    public void addAllAppointments(Collection<String> appointments) {
        this.appointments.addAll(appointments);
    }

    public void addAllMedications(Collection<Medication> medications) {
        medications.forEach(medication -> {
            this.medications.add(medication);
            medication.getPrescriptions().add(this);
        });
    }


}
