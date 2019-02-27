package com.indavara.contactsapi.service;

import com.indavara.contactsapi.database.repositories.ContactMongoRepository;
import com.indavara.contactsapi.model.Contact;
import com.indavara.contactsapi.model.ContactList;
import com.indavara.contactsapi.model.ContactSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Transactional
@Service
public class MongoDBContactService implements ContactService {

    @Autowired
    private ContactMongoRepository contactMongoRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private LinkGeneratorService linkGeneratorService;

    @Override
    public String createContact(Contact contact) {
        Contact savedEntity = contactMongoRepository.save(contact);
        return savedEntity.getContactId();
    }

    @Transactional(readOnly = true)
    @Override
    public ContactList getContacts(Integer page, Integer size) {

        ContactList contactList = new ContactList();

        if (page!= null && size!= null) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Contact> pagedContacts = contactMongoRepository.findAll(pageRequest);
            contactList.setContacts(pagedContacts.getContent());
            contactList.add(linkGeneratorService.generatePagedContactsLink(page+1, size));
        } else {
            contactList.setContacts(contactMongoRepository.findAll());
        }
        return contactList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Contact> searchContact(ContactSearchRequest contactSearchRequest) {

        Query query = new Query();
        addInCriteria(query, contactSearchRequest.getCountryCodes(), "address.countryCode");
        addInCriteria(query, contactSearchRequest.getEmails(), "email");
        addInCriteria(query, contactSearchRequest.getMobileNumbers(), "mobileNumber");
        addInCriteria(query, contactSearchRequest.getFirstNames(), "firstName");
        addInCriteria(query, contactSearchRequest.getLastNames(), "lastName");
        addInCriteria(query, contactSearchRequest.getContactIds(), "contactId");

        List<Contact> contacts = mongoTemplate.find(query, Contact.class);

        return contacts;

    }

    @Transactional(readOnly = true)
    @Override
    public void deleteContact(String email) {
        contactMongoRepository.deleteByEmail(email);
    }

    private void addInCriteria(Query query, List<String> countryCodes, String s) {
        if (isNotEmptyStrings(countryCodes)) {
            query.addCriteria(Criteria.where(s).in(countryCodes));
        }
    }

    private static boolean isNotEmptyStrings(List<String> stringList) {
        return !CollectionUtils.isEmpty(stringList);
    }

}
