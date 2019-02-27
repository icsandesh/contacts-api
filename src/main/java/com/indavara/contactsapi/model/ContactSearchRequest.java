package com.indavara.contactsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ContactSearchRequest {


    private List<String> contactIds;

    private List<String> firstNames;

    private List<String> lastNames;

    private List<String> mobileNumbers;

    private List<String> countryCodes;

    private List<String> emails;

}
