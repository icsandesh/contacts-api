package com.indavara.contactsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Contact  {

    @Id
    private String contactId;

    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String countryCode;

    private String email;

    private Address address;

}
