package com.tts.springdemo.service;

import com.tts.springdemo.model.Person;

import java.util.List;

// This interface will serve as a contract that dictates what your app will have
public interface PersonService {

    // get method by ID value
    Person getPersonById(Long id);

    // get all persons
    List<Person> getAllPersons();

    // get all from provided IDs
    List<Person> getAllById(List<Long> id);

    // get Person from provided firstname
    List<Person> getPersonByFirstName(String firstName);

}
