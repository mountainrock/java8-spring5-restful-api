package uk.co.bri8.spring.rest.model;

import java.util.concurrent.atomic.AtomicLong;

public class Person {
    private static final AtomicLong counter = new AtomicLong();

    private Long id;
    private String firstName;
    private String lastName;

    private Person() {
        // empty
    }

    public Person(String firstName, String lastName) {
        this.id = counter.incrementAndGet();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Override
    public boolean equals(Object obj) {
    	boolean isEqual = false;
    	if(obj!=null && obj instanceof Person ){
    		Person p = (Person)obj;
    		isEqual = (this.id).equals(p.getId()) &&
    				(this.firstName).equals(p.getFirstName()) && 
    				(this.lastName).equals(p.getFirstName());
        	
    		
    	}
    	return isEqual;
    }
    
    @Override
    public int hashCode() {
    	Integer hash = 7;
        hash = (int) (31 * hash +  id);
        hash = 31 * hash + (firstName == null ? 0 : firstName.hashCode());
        hash = 31 * hash + (lastName == null ? 0 : lastName.hashCode());
        return hash;
    }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder("[ id : ")
    							.append(id)
    							.append(", firstName : ").append(firstName)
    							.append(", lastName : ").append(lastName)
    							.append("]");
    	
    	return sb.toString();
    }
}
