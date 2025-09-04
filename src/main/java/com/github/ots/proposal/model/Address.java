package com.github.ots.proposal.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    private String city;
    private String district;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String floorNumber;
    private Double latitude;
    private Double longitude;
    private String dumpFullAddress;

}
