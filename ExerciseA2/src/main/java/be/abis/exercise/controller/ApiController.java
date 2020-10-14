package be.abis.exercise.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.abis.exercise.exception.PersonCanNotBeDeletedException;
import be.abis.exercise.model.Address;
import be.abis.exercise.model.Company;
import be.abis.exercise.model.Login;
import be.abis.exercise.model.Person;
import be.abis.exercise.service.PersonService;

@RestController
public class ApiController {

	@Autowired 
	PersonService myPersonService;
	
	@GetMapping(value="/person", params="id")
    public Person findPersonById(@RequestParam("id") int id)
	{
    	return myPersonService.findPerson(id);    	
    }

	@GetMapping(value="/person")
	public ArrayList<Person> getAllPersons(){
		return myPersonService.getAllPersons();
	}
	
	@GetMapping(value="/person", params="emailAddress, passWord")
    public Person findPersonByMailAndPassWord(@RequestParam("emailAddress") String emailAddress, @RequestParam("passWord") String passWord)
	{
    	return myPersonService.findPerson(emailAddress, passWord);    	
    }
	
	@PostMapping("/person")
    public void addPerson(@RequestBody Person person) throws IOException 
	{
		//System.out.println("Person: " +person.toString());
		myPersonService.addPerson(person);
    }
	
	@DeleteMapping(value="/person", params="id")
    public void deletePerson(@RequestParam("id") int id) throws Exception 
	{
		myPersonService.deletePerson(id);
    }	
	
	@PutMapping(value="/person")
    public void changePassword(@RequestBody int id, @RequestBody Person person) throws IOException  
	{
		System.out.println("Person: " +person.toString());
		myPersonService.changePassword(myPersonService.findPerson(id), person.getPassword());
    }
}
