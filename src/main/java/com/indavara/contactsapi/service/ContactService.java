package com.indavara.contactsapi.service;

import com.indavara.contactsapi.model.Contact;
import com.indavara.contactsapi.model.ContactList;
import com.indavara.contactsapi.model.ContactSearchRequest;

public interface ContactService {

    String createContact(Contact contact);

    void updateContact(Contact contact);

    ContactList getAllContactsPaginated(Integer page, Integer size);

    ContactList searchContact(ContactSearchRequest contactSearchRequest, Integer page, Integer size);

    void deleteContact(String email);
}
