package com.github.ots.proposal.controller;

import com.github.ots.common.search.pagination.PageRequestDto;
import com.github.ots.proposal.service.PrescriptionExcelReader;
import com.github.ots.proposal.service.ProposalManagementService;
import com.github.ots.proposal.specification.PrescriptionSpecifications;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/prescriptions")
@RequiredArgsConstructor
@Tag(name = "Prescription Management")
public class PrescriptionController {

    private final PrescriptionExcelReader prescriptionExcelReader;
    private final ProposalManagementService proposalManagementService;

    @PostMapping("/import")
    public Object uploadExcel(@RequestPart("file") MultipartFile file) {
        return prescriptionExcelReader.uploadAndSavePrescriptionExcelData(file);
    }


    @GetMapping("/search")
    public Object search(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String patientInfo,
            @RequestParam(required = false) String mobileNumber,
            @RequestParam(required = false) Long prescriptionId,
            @RequestParam(required = false) String appointmentId,
            @RequestParam(required = false) String medicationName
    ) {
        var specs = List.of(
                PrescriptionSpecifications.hasPatientInfo(patientInfo)
                , PrescriptionSpecifications.hasPrescriptionId(prescriptionId)
                , PrescriptionSpecifications.hasMobileNumber(mobileNumber)
                , PrescriptionSpecifications.hasAppointmentId(appointmentId)
                , PrescriptionSpecifications.hasMedicationName(medicationName)
        );
        var pageRequest = PageRequestDto.of(pageNumber, pageSize);
        return proposalManagementService.searchBasicPrescription(specs, pageRequest);
    }

}
