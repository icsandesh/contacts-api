package com.indavara.contactsapi.service;

import com.indavara.contactsapi.model.Contact;
import com.indavara.contactsapi.model.ContactSearchRequest;

import java.util.List;

public interface ContactService {

    String createContact(Contact contact);

    List<Contact> getContact(String email);

    List<Contact> searchContact(ContactSearchRequest contactSearchRequest);

    void deleteContact(String email);
}
