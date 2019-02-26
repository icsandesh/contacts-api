package com.indavara.contactsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Address {

    private String street;

    private String city;

    private String state;

    private String countryCode;

    private String pinCode;
}
