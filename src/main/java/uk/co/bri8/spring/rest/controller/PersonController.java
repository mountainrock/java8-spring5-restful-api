package uk.co.bri8.spring.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import uk.co.bri8.spring.rest.common.ServiceException;
import uk.co.bri8.spring.rest.model.Person;
import uk.co.bri8.spring.rest.referencedata.PersonDataService;

@RestController
public class PersonController {
    private PersonDataService personDataService;

    public PersonController(@Autowired PersonDataService personDataService) {
        this.personDataService = personDataService;
    }

    @GetMapping("/person/{lastName}/{firstName}")
    public Person person(@PathVariable(value="lastName") String lastName,
                         @PathVariable(value="firstName") String firstName) {
    	Optional<Person> person = personDataService.findPerson(lastName, firstName);
        Person validPerson = person.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Person not found %s %s",firstName, lastName)));
		return validPerson;
    }
    
    @GetMapping("/persons/{lastName}")
    public List<Person> personListBySurname(@PathVariable(value = "lastName") String lastName) {
        List<Person> personList = personDataService.findPersonList(lastName);
		
        if (personList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Person not found with sur name : %s", lastName));
        }
        return personList;
    }
    

    @PostMapping("/person/{lastName}/{firstName}")
    public Person addPerson(@PathVariable(value = "lastName") String lastName,
                             @PathVariable(value = "firstName") String firstName) {
    	Person addPerson = null;
    	try{
        	addPerson = personDataService.addPerson(firstName, lastName);
        }catch(ServiceException se){
        	 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(" %s", se.getErrorMessage()));
        }
        return addPerson;
    }
}