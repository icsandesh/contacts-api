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

import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.List;

import static com.indavara.contactsapi.util.CommonUtils.buildInCriteria;

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


        List<Contact> byEmailContacts = contactMongoRepository.findByEmail(contact.getEmail());
        if(!CollectionUtils.isEmpty(byEmailContacts)){
            throw new BadRequestException("emailId is already in use");
        }
        Contact savedEntity = contactMongoRepository.save(contact);
        return savedEntity.getContactId();
    }

    @Override
    public void updateContact(Contact contact) {

        List<Contact> byEmail = contactMongoRepository.findByEmail(contact.getEmail());
        if(CollectionUtils.isEmpty(byEmail)){
            throw new BadRequestException("cannot update. Email does not exist");
        }

        contactMongoRepository.save(contact);
    }

    @Transactional(readOnly = true)
    @Override
    public ContactList getAllContacts(Integer page, Integer size) {

        ContactList contactList = new ContactList();

        if (page != null && size != null) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Contact> pagedContacts = contactMongoRepository.findAll(pageRequest);
            contactList.setContacts(pagedContacts.getContent());
            addNextPageLink(page, size, contactList);
        } else {
            contactList.setContacts(contactMongoRepository.findAll());
        }
        return contactList;
    }


    @Transactional(readOnly = true)
    @Override
    public ContactList searchContact(ContactSearchRequest contactSearchRequest, Integer page, Integer size) {

        Query query = new Query();
        query.with(PageRequest.of(page, size));

        List<Criteria> queryCriteriaList = buildQueryCriteria(contactSearchRequest);
        queryCriteriaList.forEach(query::addCriteria);

        List<Contact> contacts = mongoTemplate.find(query, Contact.class);

        return buildSearchResponse(page, size, contacts);
    }

    private ContactList buildSearchResponse(Integer page, Integer size, List<Contact> contacts) {
        ContactList contactList = new ContactList();
        contactList.setContacts(contacts);
        addNextPageLink(page, size, contactList);
        return contactList;
    }

    private List<Criteria> buildQueryCriteria(ContactSearchRequest contactSearchRequest) {
        List<Criteria> queryCriteriaList = new ArrayList<>();
        queryCriteriaList.addAll(buildInCriteria("address.countryCode", contactSearchRequest.getCountryCodes()));
        queryCriteriaList.addAll(buildInCriteria("email", contactSearchRequest.getEmails()));
        queryCriteriaList.addAll(buildInCriteria("mobileNumber", contactSearchRequest.getMobileNumbers()));
        queryCriteriaList.addAll(buildInCriteria("firstName", contactSearchRequest.getFirstNames()));
        queryCriteriaList.addAll(buildInCriteria("lastName", contactSearchRequest.getLastNames()));
        queryCriteriaList.addAll(buildInCriteria("contactId", contactSearchRequest.getContactIds()));
        return queryCriteriaList;
    }


    @Transactional(readOnly = true)
    @Override
    public void deleteContact(String email) {
        contactMongoRepository.deleteByEmail(email);
    }


    private void addNextPageLink(Integer page, Integer size, ContactList contactList) {
        if(contactList.getContacts().size() > size) {
            contactList.add(linkGeneratorService.generatePagedContactsLink(page + 1, size));
        }
    }


}
