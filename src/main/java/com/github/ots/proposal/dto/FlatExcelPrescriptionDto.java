package com.github.ots.proposal.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlatExcelPrescriptionDto {

    private String patientId;
    private String patientName;
    private String patientMobile;
    private String location;

    private String insuranceCompany;
    private LocalDate insuranceExpiryDate;

    private LocalDate prescriptionDate;
    private String clinicName;

    private LocalDate avgMedFinishDate;

    private LocalDate callingDate;

    private List<String> medications;

    private List<String> appointments;

}
