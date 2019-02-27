package com.indavara.contactsapi.controller;

import com.indavara.contactsapi.model.Contact;
import com.indavara.contactsapi.model.ContactSearchRequest;
import com.indavara.contactsapi.service.MongoDBContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class ContactController {


    @Autowired
    private MongoDBContactService contactService;



    @RequestMapping(value = "v1/contacts", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createContact(@RequestBody Contact contactDTO){

        String contactId = contactService.createContact(contactDTO);
        return new ResponseEntity<>(contactId, HttpStatus.CREATED);
    }


    @RequestMapping(value = "v1/contacts/search", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Contact>> searchContact(@RequestBody ContactSearchRequest contactSearchRequest){

        List<Contact> contacts = contactService.searchContact(contactSearchRequest);
        return new ResponseEntity<>(contacts, HttpStatus.CREATED);
    }


    @RequestMapping(value = "v1/contacts", method = RequestMethod.GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Contact>> getContact(@RequestParam(value = "email",required = false) String email){
        List<Contact> contacts = contactService.getContact(email);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @RequestMapping(value = "v1/contacts", method = RequestMethod.DELETE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Contact> deleteContact(@RequestParam("email") String email){
        contactService.deleteContact(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
