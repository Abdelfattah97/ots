package com.github.ots.proposal.mapper;

import com.github.ots.proposal.dto.ExcelPrescriptionDto;
import com.github.ots.proposal.dto.FlatExcelPrescriptionDto;
import com.github.ots.proposal.dto.basic.BasicPrescriptionDto;
import com.github.ots.proposal.model.Medication;
import com.github.ots.proposal.model.Prescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = {PatientMapper.class, MedicationMapper.class
                , PharmacistMapper.class})
public interface PrescriptionMapper {

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "refillStatus", ignore = true)
    @Mapping(target = "prescriptionId", ignore = true)
    @Mapping(target = "pharmacist", ignore = true)
    @Mapping(target = "patient", source = "patientDto")
    @Mapping(target = "medications")
    Prescription mapToEntity(ExcelPrescriptionDto excelRecord);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medicationName", source = "medication")
    Medication mapToMedication(String medication);

    List<Prescription> mapToEntity(List<ExcelPrescriptionDto> excelRecords);


    @Mapping(target = "patient.fullName", source = "patientName")
    @Mapping(target = "patient.patientId", source = "patientId")
    @Mapping(target = "patient.insuranceExpiryDate", source = "insuranceExpiryDate")
    @Mapping(target = "patient.insuranceCompany", source = "insuranceCompany")
    @Mapping(target = "patient.mobileNumber", source = "patientMobile")
    @Mapping(target = "patient.address.dumpFullAddress", source = "location")
    @Mapping(target = "refillStatus", ignore = true)
    @Mapping(target = "prescriptionId", ignore = true)
    @Mapping(target = "pharmacist", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Prescription mapToEntity(FlatExcelPrescriptionDto dto);

    BasicPrescriptionDto mapToBasicDto(Prescription prescription);

    default List<BasicPrescriptionDto> mapToBasicDto(List<Prescription> prescriptions) {
        return prescriptions.stream().map(this::mapToBasicDto).toList();
    }

}
