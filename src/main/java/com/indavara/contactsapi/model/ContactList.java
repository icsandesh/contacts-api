package com.indavara.contactsapi.model;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Data
public class ContactList extends ResourceSupport {

    List<Contact> contacts;
}
