package com.github.ots.proposal.mapper;

import com.github.ots.proposal.dto.basic.BasicMedicationDto;
import com.github.ots.proposal.model.Medication;
import jakarta.persistence.Basic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicationMapper {

    @Mapping(target = "medicationId", source = "id")
    BasicMedicationDto mapToBasicDto(Medication medication);

    default List<BasicMedicationDto> mapToBasicDto(Collection<Medication> medications) {
        return medications.stream().map(this::mapToBasicDto).toList();
    }
}
