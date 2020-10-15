package be.abis.exercise.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import be.abis.exercise.exception.PersonCanNotBeDeletedException;
import be.abis.exercise.model.Login;
import be.abis.exercise.model.Person;

@Service
public class ApiPersonService implements PersonService 
{
    
	@Autowired
	private RestTemplate myRestTemplate;
	private String myBaseUri = "http://localhost:8085/exercise/api/persons";

	@Override
	public Person findPerson(int id) 
	{
		Person foundPerson = myRestTemplate.getForObject(myBaseUri+"/"+id,Person.class);
		return foundPerson;
	}

	@Override
	public ArrayList<Person> getAllPersons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person findPersonByLogin(Login login) 
	{
		myRestTemplate.postForObject(myBaseUri+"/login", login, Person.class);
		return new Person();
	}

	@Override
	public void addPerson(Person p) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePerson(int id) throws PersonCanNotBeDeletedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePassword(Person p, String newPswd) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
