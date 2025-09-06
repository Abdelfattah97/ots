package com.github.ots.proposal.dto.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.ots.proposal.enums.RefillStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class BasicPrescriptionDto {

    private Long prescriptionId;

    private BasicPatientDto patient;

    private LocalDate prescriptionDate;

    private Set<BasicMedicationDto> medications;

    private LocalDate avgMedFinishDate;

    private String clinicName;

    private LocalDate callingDate;

    @JsonProperty("status")
    private RefillStatus refillStatus;

    private BasicPharmacistDto pharmacist;

    private Set<String> appointments ;

}
