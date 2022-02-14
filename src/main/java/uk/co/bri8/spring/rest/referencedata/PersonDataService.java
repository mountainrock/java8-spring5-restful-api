package uk.co.bri8.spring.rest.referencedata;

import org.springframework.stereotype.Service;

import uk.co.bri8.spring.rest.common.ServiceException;
import uk.co.bri8.spring.rest.model.Person;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonDataService {
    public static final List<Person> PERSON_DATA = Arrays.asList(
        new Person("Mary", "Smith"),
        new Person("Brian", "Archer"),
        new Person("Collin", "Brown")
    );

    public Optional<Person> findPerson(String lastName, String firstName) {
        return PERSON_DATA.stream()
            .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                && p.getLastName().equalsIgnoreCase(lastName))
            .findAny();
    }

	public List<Person> findPersonList(String lastName) {
		 List<Person> personList = PERSON_DATA.stream()
		            .filter(p-> p.getLastName().equalsIgnoreCase(lastName))
		            .collect(Collectors.toList());
		return personList;
	}

	public Person addPerson(String firstName, String lastName) {
	    boolean isPersonPresent = this.findPerson(lastName, firstName).isPresent();
	    if(isPersonPresent) {
	            throw new ServiceException(String.format("Person exists with first name : %s, last name :%s", firstName, lastName));
	    }
        Person newPerson = new Person(firstName, lastName);
        PERSON_DATA.add(newPerson);
	    return newPerson;
	}
}
