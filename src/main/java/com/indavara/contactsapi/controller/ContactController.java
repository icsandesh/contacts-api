package com.indavara.contactsapi.controller;

import com.indavara.contactsapi.model.Contact;
import com.indavara.contactsapi.model.ContactList;
import com.indavara.contactsapi.model.ContactSearchRequest;
import com.indavara.contactsapi.service.MongoDBContactService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;


@RestController
@Api(value = "Contact book API", description = "Crud operations on contact book.")
public class ContactController {


    @Autowired
    private MongoDBContactService contactService;


    @RequestMapping(value = "v1/contacts", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createContact(@RequestBody @Valid Contact contactDTO){

        String contactId = contactService.createContact(contactDTO);
        return new ResponseEntity<>(contactId, HttpStatus.CREATED);
    }


    @RequestMapping(value = "v1/contacts/search", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ContactList> searchContact(@RequestBody ContactSearchRequest contactSearchRequest, @RequestParam(value = "page", required = false)Integer page,
                                                       @RequestParam(value = "size", required = false) Integer size){


        if(page == null ){ page =0;}
        if(size == null) {size = 10;} // size defaulted to 10

        ContactList contacts = contactService.searchContact(contactSearchRequest, page, size);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @RequestMapping(value = "v1/contacts/{contactId}", method = PUT, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateContact(@RequestBody @Valid Contact contact){

        contactService.updateContact(contact);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }


    @RequestMapping(value = "v1/contacts", method = RequestMethod.GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ContactList> getAllContacts(@RequestParam(value = "page",required = false) Integer page,
                                                        @RequestParam(value = "size", required = false) Integer size){
        ContactList contactList = contactService.getAllContactsPaginated(page, size);
        return new ResponseEntity<>(contactList, HttpStatus.OK);
    }

    @RequestMapping(value = "v1/contacts", method = RequestMethod.DELETE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Contact> deleteContact(@RequestParam("email") String email){
        contactService.deleteContact(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
