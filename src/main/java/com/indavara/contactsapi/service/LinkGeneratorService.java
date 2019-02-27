package com.indavara.contactsapi.service;

import com.indavara.contactsapi.controller.ContactController;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class LinkGeneratorService {


    public static final String NEXT_PAGE_LINK = "nextPageLink";

    public Link generatePagedContactsLink(int page, int size){
        return linkTo(methodOn(ContactController.class).getAllContacts(page, size)).withRel(NEXT_PAGE_LINK);
    }

}
