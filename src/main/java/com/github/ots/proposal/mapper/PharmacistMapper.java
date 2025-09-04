package com.github.ots.proposal.mapper;

import com.github.ots.proposal.dto.basic.BasicPharmacistDto;
import com.github.ots.proposal.model.Pharmacist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PharmacistMapper {

    @Mapping(target = "pharmacistId", source = "id")
    @Mapping(target = "pharmacistName", source = "name")
    BasicPharmacistDto mapToBasicDto(Pharmacist pharmacist);

}
