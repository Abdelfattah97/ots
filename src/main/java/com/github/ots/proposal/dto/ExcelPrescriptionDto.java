package com.github.ots.proposal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
public class ExcelPrescriptionDto {

    private ExcelPatientDto patientDto;

    private String insuranceCompany;
    private LocalDate insuranceExpiryDate;

    private LocalDate prescriptionDate;
    private String clinicName;

    private LocalDate avgMedFinishDate;

    private LocalDate callingDate;

    private Set<String> medications;

    private Set<String> appointments;

}
