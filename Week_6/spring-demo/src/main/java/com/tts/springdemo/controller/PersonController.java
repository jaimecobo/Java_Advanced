package com.tts.springdemo.controller;

import com.tts.springdemo.model.Person;
import com.tts.springdemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/get/all")
    public List<Person> getAllPersons(){
        return (List<Person>) personRepository.findAll();
    }

    //Bellow we are utilizing a path variable
    //this allows us to generate unique urls for unique resources
    @GetMapping("/get/{id}")
    public Person getPersonById(@PathVariable Long id){
        return personRepository.findById(id).orElseThrow();
    }

    // Using this example URl ** http://localhost:8080/person/get/personById?id=3 **
    // You can get the all the data of object with ID 3
    @GetMapping("get/personById")
    public Optional<Person> getPersons (@RequestParam Long id){
        return personRepository.findById(id);
    }

    //-------------------------------------------------------------------------------------------------------
    // BELOW WE ARE UTILIZING REQUEST PARAMETERS WHICH ALLOWS US TO PERFORM SEARCHES IN A STANDARDIZED FASHION
    // WE QUERY THE DATABASE USING SPECIFIC VALUES AN RESOLVE THE CODE
    //-------------------------------------------------------------------------------------------------------

    // Using this example URL ** http://localhost:8080/person/get/persons?id=1&id=3 **
    // you can get the all the data of objects with IDs 1 and 3
    // You can also use this URL with commas to separate ID values ** http://localhost:8080/person/get/persons?id=1,2,3 **
    @GetMapping("get/persons")
    public Iterable<Person> getPersons (@RequestParam List<Long> id){
        return personRepository.findAllById(id);
    }

    // Using this example URL ** http://localhost:8080/person/search?firstName=Donald **
    // you can get all the data of an object identified by firstname
    @GetMapping("/search")
    public Iterable<Person> searchPersonByName(@RequestParam String firstName){
        return personRepository.findAllByFirstName(firstName);
    }



}
