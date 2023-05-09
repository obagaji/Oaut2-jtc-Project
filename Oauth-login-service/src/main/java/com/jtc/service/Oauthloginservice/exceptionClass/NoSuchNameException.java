package com.jtc.service.Oauthloginservice.exceptionClass;

import com.jtc.service.Oauthloginservice.oauth2Entity.PersonEntity;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;
@Component
public class NoSuchNameException extends Exception implements Supplier<PersonEntity> {
    @Override
    public PersonEntity get() {
        PersonEntity person = new PersonEntity();
        person.setEmail(" No Email");
        person.setPasswords(" No Password Entered");
        person.setUsername(" No Name Found");
        person.setRoles(" No Role Associated");
        return person;
    }
}
