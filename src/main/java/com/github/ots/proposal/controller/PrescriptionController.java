package com.github.ots.proposal.controller;

import com.github.ots.common.search.pagination.PageRequestDto;
import com.github.ots.proposal.model.Prescription;
import com.github.ots.proposal.service.PrescriptionExcelReader;
import com.github.ots.proposal.service.ProposalManagementService;
import com.github.ots.proposal.specification.PrescriptionSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionExcelReader prescriptionExcelReader;
    private final ProposalManagementService proposalManagementService;

    @PostMapping("/upload-excel")
    public Object uploadExcel(@RequestPart("file") MultipartFile file) {
        return prescriptionExcelReader.uploadAndSavePrescriptionExcelData(file);
    }

    @GetMapping("/search")
    public Object search(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) String mobileNumber,
            @RequestParam(required = false) Long prescriptionId,
            @RequestParam(required = false) String appointmentId
    ) {
        var specs = List.of(
                PrescriptionSpecifications.hasPatientId(patientId)
                , PrescriptionSpecifications.hasPatientName(patientName)
                , PrescriptionSpecifications.hasPrescriptionId(prescriptionId)
                , PrescriptionSpecifications.hasMobileNumber(mobileNumber)
                , PrescriptionSpecifications.hasAppointmentId(appointmentId)
        );
        var pageRequest = PageRequestDto.of(pageNumber, pageSize);
        return proposalManagementService.searchBasicPrescription(specs, pageRequest);
    }

}
