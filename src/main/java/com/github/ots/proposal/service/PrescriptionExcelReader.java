package com.github.ots.proposal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ots.attachment.dto.SavableAttachmentDto;
import com.github.ots.attachment.service.AttachmentService;
import com.github.ots.attachment.util.AttachmentUtil;
import com.github.ots.attachment.validator.AttachmentValidator;
import com.github.ots.common.util.JsonUtil;
import com.github.ots.excel.dto.ExcelColConfigDto;
import com.github.ots.excel.dto.ExcelTableConfigDto;
import com.github.ots.excel.service.ExcelReaderService;
import com.github.ots.proposal.dto.FlatExcelPrescriptionDto;
import com.github.ots.proposal.dto.basic.BasicPrescriptionDto;
import com.github.ots.proposal.enums.RefillStatus;
import com.github.ots.proposal.mapper.PrescriptionMapper;
import com.github.ots.proposal.model.Medication;
import com.github.ots.proposal.model.Patient;
import com.github.ots.proposal.model.Prescription;
import com.github.ots.proposal.repository.MedicationRepository;
import com.github.ots.proposal.repository.PatientRepository;
import com.github.ots.proposal.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrescriptionExcelReader {

    private final PrescriptionRepository prescriptionRepository;
    private final ExcelReaderService excelReaderService;
    private final AttachmentService attachmentService;
    private final PrescriptionMapper prescriptionMapper;
    private final PatientRepository patientRepository;
    private final MedicationRepository medicationRepository;

    @Transactional
    public List<BasicPrescriptionDto> uploadAndSavePrescriptionExcelData(MultipartFile file) {
        SavableAttachmentDto attachmentDto;
        try {
            attachmentDto = SavableAttachmentDto.builder()
                    .fileName(file.getOriginalFilename())
                    .mimeType(AttachmentUtil.getExtension(file))
                    .data(file.getResource().getContentAsByteArray())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AttachmentValidator.validate(attachmentDto);

        try {
            return prescriptionMapper.mapToBasicDto(readAndSavePrescriptionsFromExcel(file.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read excel file", e);
        }
    }

    @Transactional
    public List<Prescription> readAndSavePrescriptionsFromExcel(InputStream inputStream) {
        ExcelTableConfigDto configDto = ExcelTableConfigDto.builder()
                .colStart(0)
                .colEnd(23)
                .rowStart(0)
                .inputStream(inputStream)
                .isHeaderFirstRow(true)
                .sheetNum(0)
                .colConfigList(getColConfigList())
                .build();
        List<Map<String, Object>> data = null;
        try {
            data = excelReaderService.readExcelTable(configDto);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read excel file", e);
        }
        ObjectMapper mapper = JsonUtil.objectMapper;

        List<FlatExcelPrescriptionDto> prescriptions = data.stream()
                .map(row -> {
                            try {
                                return mapper.convertValue(row, FlatExcelPrescriptionDto.class);
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }
                            return null;
                        }
                )
                .filter(Objects::nonNull)
                .toList();
        log.info("parsed ({}) prescriptions", prescriptions.size());

        return saveAll(prescriptions.stream().map(prescriptionMapper::mapToEntity).collect(Collectors.toList()));
    }

    @Transactional
    protected List<Prescription> saveAll(List<Prescription> prescriptions) {
        List<Patient> patients = prescriptions.stream()
                .map(Prescription::getPatient)
                .toList();
        Map<String, Patient> patientsMap = patientRepository.findAllById(patients.stream().map(Patient::getPatientId).toList())
                .stream().collect(Collectors.toMap(Patient::getPatientId, p -> p));
        for (Patient patient : patients) {
            patientsMap.merge(patient.getPatientId(), patient, (p1, p2) -> p1);
        }

        Set<Medication> medications = prescriptions.stream()
                .map(Prescription::getMedications)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        Map<String, Medication> medicationsMap = medicationRepository.findAllByMedicationNameIn(medications.stream()
                        .map(Medication::getMedicationName).toList())
                .stream().collect(Collectors.toMap(Medication::getMedicationName, med -> med));
        for (Medication medication : medications) {
            medicationsMap.putIfAbsent(medication.getMedicationName(), medication);
        }
        prescriptions.forEach(prescription -> {
            Set<Medication> medicationSet = prescription.getMedications().stream().map(Medication::getMedicationName)
                    .map(medicationsMap::get).collect(Collectors.toSet());
            prescription.getMedications().clear();
            prescription.addAllMedications(medicationSet);
            prescription.setPatient(patientsMap.get(prescription.getPatient().getPatientId()));
            prescription.setRefillStatus(RefillStatus.NEW);
        });

        medicationRepository.saveAll(medicationsMap.values());
        patientRepository.saveAll(patientsMap.values());
        return prescriptionRepository.saveAll(prescriptions);
    }

    private List<ExcelColConfigDto> getColConfigList() {
        return
                List.of(
                        ExcelColConfigDto.builder()
                                .colAddress("A")
                                .colAlias("patientId")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("B")
                                .colAlias("patientName")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("C")
                                .colAlias("patientMobile")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("D")
                                .colAlias("insuranceCompany")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("E")
                                .colAlias("prescriptionDate")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("F")
                                .colAlias("insuranceExpiryDate")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("G")
                                .colAlias("clinicName")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("H")
                                .colAlias("dispensedProject")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("I")
                                .colAlias("curentActiveCompanyName")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("J")
                                .colAlias("curentActiveCompanyExpiry")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("K")
                                .colAlias("avgMedFinishDate")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("L")
                                .colAlias("column2")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("M")
                                .colAlias("column3")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("N")
                                .colAlias("callingDate")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("O")
                                .colAlias("medications")
                                .colType(List.class)
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("P")
                                .colAlias("appointments")
                                .colType(List.class)
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("Q")
                                .colAlias("pharmacyName")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("R")
                                .colAlias("pharmacistName")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("S")
                                .colAlias("patientCalledOrNot")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("T")
                                .colAlias("unifiedResult")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("U")
                                .colAlias("location")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("V")
                                .colAlias("remarks1")
                                .build(),

                        ExcelColConfigDto.builder()
                                .colAddress("W")
                                .colAlias("remarks2")
                                .build()
                );
    }

}
