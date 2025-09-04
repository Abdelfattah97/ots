package com.github.ots.proposal.dto.basic;

import com.github.ots.proposal.model.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BasicPatientDto {
    private String patientId;
    private String patientName;
    private String mobileNumber;
    private BasicAddressDto address;
    private String insuranceCompany;
    private LocalDate insuranceExpiryDate;
}
