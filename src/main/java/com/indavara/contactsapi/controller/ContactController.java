package com.indavara.contactsapi.controller;

import com.indavara.contactsapi.model.Contact;
import com.indavara.contactsapi.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class ContactController {


    @Autowired
    private ContactService contactService;


    @RequestMapping(value = "/v1/contacts", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createContact(@RequestBody Contact contactDTO){

        String contactId = contactService.createContact(contactDTO);

        return new ResponseEntity<>(contactId, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/v1/contacts", method = RequestMethod.GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Contact> getContact(@RequestParam("email") String email){

        Contact contact = contactService.getContact(email);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/contacts", method = RequestMethod.DELETE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Contact> deleteContact(@RequestParam("email") String email){
        contactService.deleteContact(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
