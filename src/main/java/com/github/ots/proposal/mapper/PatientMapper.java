package com.github.ots.proposal.mapper;

import com.github.ots.proposal.dto.basic.BasicPatientDto;
import com.github.ots.proposal.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = AddressMapper.class)
public interface PatientMapper {

    @Mapping(target = "patientName", source = "fullName")
    BasicPatientDto mapToBasicDto(Patient patient);

}
