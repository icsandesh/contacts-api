package com.indavara.contactsapi.service;

import com.indavara.contactsapi.database.repositories.ContactMongoRepository;
import com.indavara.contactsapi.model.Address;
import com.indavara.contactsapi.model.Contact;
import com.indavara.contactsapi.model.ContactList;
import com.indavara.contactsapi.model.ContactSearchRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.ws.rs.BadRequestException;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MongoDBContactServiceTest {

    @Mock
    private ContactMongoRepository contactMongoRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private MongoDBContactService mongoDBContactService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = BadRequestException.class)
    public void verifyBadCreationRequestWhenEmailAlreadyExistsInDB() {

        Contact contactMock = getContactMock();
        when(contactMongoRepository.findByEmail(contactMock.getEmail())).thenReturn(Arrays.asList(contactMock));
        mongoDBContactService.createContact(contactMock);
    }

    @Test(expected = Exception.class)
    public void verifyBadRequestIfEmailNotPresentInCreateRequest() {
        Contact contactMock = getContactMock();
        contactMock.setEmail(null);
        mongoDBContactService.createContact(contactMock);
    }

    @Test(expected = Exception.class)
    public void verifyBadRequestIfNameNotPresentInCreateRequest() {
        Contact contactMock = getContactMock();
        contactMock.setFirstName(null);
        mongoDBContactService.createContact(contactMock);
    }


    @Test(expected = BadRequestException.class)
    public void verifyUpdateRequestIfEmailDoesNotExistInDB() {

        when(contactMongoRepository.findByEmail(getContactMock().getEmail())).thenReturn(Collections.emptyList());
        mongoDBContactService.updateContact(getContactMock());
    }


    @Test
    public void verifyCriteriaCreatedForSearchRequest(){

        ContactSearchRequest contractRequestMock = getContractRequestMock();

        Query mockQUery = new Query();
        mockQUery.addCriteria(Criteria.where("email").in(contractRequestMock.getEmails()));
        mockQUery.addCriteria(Criteria.where("mobileNumber").in(contractRequestMock.getMobileNumbers()));
        mockQUery.with(PageRequest.of(0, 5));

        when(mongoTemplate.find(mockQUery, Contact.class)).thenReturn(Arrays.asList(getContactMock()));

        ContactList contactList = mongoDBContactService.searchContact(getContractRequestMock(), 0, 5);
        verify(mongoTemplate).find(mockQUery, Contact.class);
        assertEquals(contactList, getContactListMock());
    }

    private ContactSearchRequest getContractRequestMock() {
        ContactSearchRequest contactSearchRequest = new ContactSearchRequest();
        contactSearchRequest.setEmails(Arrays.asList("icsandesh@gmail.com"));
        contactSearchRequest.setMobileNumbers(Arrays.asList("9482862050"));
        return contactSearchRequest;
    }

    private Contact getContactMock(){
        Contact contact = new Contact();
        contact.setFirstName("Sandesh");
        contact.setLastName("Gowda");
        contact.setEmail("icsandesh@gmail.com");
        contact.setMobileNumber("9482862050");

        Address address = new Address();
        address.setCountryCode("IN");
        address.setCity("Bengaluru");
        address.setPinCode("560022");
        contact.setAddress(address);
        return contact;
    }

    private ContactList getContactListMock(){

        ContactList contactList = new ContactList();
        contactList.setContacts(Arrays.asList(getContactMock()));
        return contactList;
    }

}