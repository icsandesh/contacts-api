package com.indavara.contactsapi.service;

import com.indavara.contactsapi.database.repositories.ContactMongoRepository;
import com.indavara.contactsapi.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DBContactService implements ContactService  {

    @Autowired
    private ContactMongoRepository contactMongoRepository;

    @Override
    public String createContact(Contact contact) {
        Contact savedEntity = contactMongoRepository.save(contact);
        return savedEntity.getContactId();
    }

    @Override
    public Contact getContact(String email) {
        return contactMongoRepository.findByEmail(email);
    }

    @Override
    public void deleteContact(String email) {
         contactMongoRepository.deleteByEmail(email);
    }
}
