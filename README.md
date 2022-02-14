# Java8 Spring5 RESTful API Test

## How to build
```./gradlew clean build```

## How to test
```./gradlew test```

## Exercises
### Exercise 1
Make the tests run green (there should be one failing test)

Solution: 
- Person.java counter was not returning unique number. Updated it to static. Also added hashcode, equals and toString. 
- Though equals/hashcode is not required when we use "id" for uniqueness by convention, have added it for illustration purpose.
- toString to dump the object member variables.


### Exercise 2
Update the existing `/person/{lastName}/{firstName}` endpoint to return an appropriate RESTful response when the requested person does not exist in the list
- prove your results

Solution :
- created junit shouldHandlePersonNotFoundFromService and updated PersonController to use Optional and handle person not found case. 


### Exercise 3
Write a RESTful API endpoint to retrieve a list of all people with a particular surname
- pay attention to what should be returned when there are no match, one match, multiple matches
- prove your results

Solution : 
- Added junit and API in PersonController :getPersons() . Handling no match case.

### Exercise 4
Write a RESTful API endpoint to add a new value to the list
- pay attention to what should be returned when the record already exists
- prove your resutls

Solution : 
- Added junit and API in PersonController :addPerson() . Handling duplicate case.
