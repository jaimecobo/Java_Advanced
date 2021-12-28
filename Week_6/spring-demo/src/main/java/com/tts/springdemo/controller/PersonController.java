package com.tts.springdemo.controller;

import com.tts.springdemo.model.Person;
import com.tts.springdemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
