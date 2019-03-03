package com.indavara.contactsapi.controller;

import com.indavara.contactsapi.model.Address;
import com.indavara.contactsapi.model.Contact;
import com.indavara.contactsapi.model.ContactList;
import com.indavara.contactsapi.model.ContactSearchRequest;
import com.indavara.contactsapi.service.MongoDBContactService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;


public class ContactControllerTest {

    public static final String ID = "12313ID";

    @Mock
    private MongoDBContactService mongoDBContactService;

    @InjectMocks
    private ContactController contactController;


    @Before
    public  void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void createContact() {

        Contact contactMock = getContactMock();
        when(mongoDBContactService.createContact(contactMock)).thenReturn(ID);

        ResponseEntity<String> responseEntity = contactController.createContact(contactMock);

        verify(mongoDBContactService).createContact(contactMock);

        assertEquals(responseEntity.getBody(), ID);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void searchContact() {

        when(mongoDBContactService.searchContact(getContactSearchRequest(),0,5)).thenReturn(getContactListMock());

        ResponseEntity<ContactList> responseEntity = contactController.searchContact(getContactSearchRequest(),0,5);

        verify(mongoDBContactService).searchContact(getContactSearchRequest(), 0,5);

        assertEquals(responseEntity.getBody(), getContactListMock());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);


    }

    @Test
    public void updateContact() {
        Contact contactMock = getContactMock();
        doNothing().when(mongoDBContactService).updateContact(contactMock);

        ResponseEntity<String> responseEntity = contactController.updateContact(contactMock);

        verify(mongoDBContactService).updateContact(contactMock);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getAllContacts() {

        when(mongoDBContactService.getAllContacts(0,5)).thenReturn(getContactListMock());

        ResponseEntity<ContactList> responseEntity = contactController.getAllContacts(0,5);

        verify(mongoDBContactService).getAllContacts(0,5);

        assertEquals(responseEntity.getBody(), getContactListMock());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void deleteContact() {

        String mockEmail = getContactMock().getEmail();
        doNothing().when(mongoDBContactService).deleteContact(mockEmail);

        ResponseEntity<Contact> responseEntity = contactController.deleteContact(mockEmail);

        verify(mongoDBContactService).deleteContact(mockEmail);

        assertNull(responseEntity.getBody());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

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

    private ContactSearchRequest getContactSearchRequest(){

        ContactSearchRequest contactSearchRequest = new ContactSearchRequest();

        contactSearchRequest.setEmails(Arrays.asList("icsandesh@gmail.com"));

        return contactSearchRequest;
    }



    private ContactList getContactListMock(){

        ContactList contactList = new ContactList();
        contactList.setContacts(Arrays.asList(getContactMock()));
        return contactList;
    }
}