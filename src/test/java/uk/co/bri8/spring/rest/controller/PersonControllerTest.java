package uk.co.bri8.spring.rest.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import uk.co.bri8.spring.rest.common.ServiceException;
import uk.co.bri8.spring.rest.controller.PersonController;
import uk.co.bri8.spring.rest.model.Person;
import uk.co.bri8.spring.rest.referencedata.PersonDataService;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonDataService personDataService;

    @Test
    public void shouldReturnPersonFromService() throws Exception {
        when(personDataService.findPerson(any(), any())).thenReturn(Optional.of(new Person("Mary", "Smith")));
        this.mockMvc.perform(get("/person/smith/mary"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("firstName").value("Mary"))
            .andExpect(jsonPath("lastName").value("Smith"));
    }
    
    @Test
    public void shouldHandlePersonNotFoundFromService() throws Exception {
        this.mockMvc.perform(get("/person/smith/john"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("id").doesNotExist())
                .andExpect(jsonPath("firstName").doesNotExist())
                .andExpect(jsonPath("lastName").doesNotExist());
    }
    
    @Test
    public void shouldReturnPersonListBySurname() throws Exception {
    	List<Person> personList = new ArrayList<Person>();
    	personList.add(new Person("Mary", "Smith"));
    	personList.add(new Person("Joe", "Smith"));
    	when(personDataService.findPersonList(any())).thenReturn(personList);
       
        this.mockMvc.perform(get("/persons/smith"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].firstName").value("Mary"))
                .andExpect(jsonPath("$[0].lastName").value("Smith"))
                .andExpect(jsonPath("$[1].firstName").value("Joe"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"));
    }
    
    
    @Test
    public void shouldReturnPersonListBySurnameNotFound() throws Exception {
        this.mockMvc.perform(get("/persons/na"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void shouldAddPerson() throws Exception {
    	when(personDataService.addPerson(any(), any())).thenReturn(new Person("Robert", "Downey"));
        this.mockMvc.perform(post("/person/Downey/Robert"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("firstName").value("Robert"))
                .andExpect(jsonPath("lastName").value("Downey"));
    }

    @Test
    public void shouldNotAddDuplicatePerson() throws Exception {
    	when(personDataService.addPerson(any(), any())).thenThrow(new ServiceException("Duplicate person found"));
        this.mockMvc.perform(post("/person/Archer/Brian"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("id").doesNotExist());
    }

}