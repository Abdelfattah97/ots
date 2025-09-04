package com.github.ots.proposal.mapper;

import com.github.ots.proposal.dto.basic.BasicAddressDto;
import com.github.ots.proposal.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    BasicAddressDto mapToBasicDto(Address address);

}
