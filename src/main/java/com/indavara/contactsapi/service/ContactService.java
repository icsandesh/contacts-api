package com.indavara.contactsapi.service;

import com.indavara.contactsapi.model.Contact;

public interface ContactService {

    String createContact(Contact contact);

    Contact getContact(String email);

    void deleteContact(String email);
}
