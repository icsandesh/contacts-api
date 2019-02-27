package com.indavara.contactsapi.service;

import com.indavara.contactsapi.model.Contact;
import com.indavara.contactsapi.model.ContactList;
import com.indavara.contactsapi.model.ContactSearchRequest;

import java.util.List;

public interface ContactService {

    String createContact(Contact contact);

    ContactList getContacts(Integer page, Integer size);

    List<Contact> searchContact(ContactSearchRequest contactSearchRequest);

    void deleteContact(String email);
}
