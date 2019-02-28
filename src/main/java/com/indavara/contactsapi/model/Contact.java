package com.indavara.contactsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document
@Data
public class Contact {

    @Id
    private String contactId;

    @NotBlank(message = "firstName cannot be blank")
    private String firstName;

    private String lastName;

    private String mobileNumber;

    @Email(message = "valid email is mandatory")
    private String email;

    private Address address;

}
