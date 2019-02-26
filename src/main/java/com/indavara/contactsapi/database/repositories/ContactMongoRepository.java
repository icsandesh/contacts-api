package com.indavara.contactsapi.database.repositories;

import com.indavara.contactsapi.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface ContactMongoRepository extends MongoRepository<Contact, String> {

    Contact findByEmail(String email);

    void deleteByEmail(String email);
}
