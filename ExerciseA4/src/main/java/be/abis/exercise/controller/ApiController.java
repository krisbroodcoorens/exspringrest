package be.abis.exercise.controller;

import java.awt.PageAttributes.MediaType;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import be.abis.exercise.exception.PersonCanNotBeDeletedException;
import be.abis.exercise.model.Address;
import be.abis.exercise.model.Company;
import be.abis.exercise.model.Login;
import be.abis.exercise.model.Person;
import be.abis.exercise.service.PersonService;

@RestController
@RequestMapping("persons")
public class ApiController {

	@Autowired 
	PersonService myPersonService;
	
	@GetMapping("{id}")
    public Person findPersonById(@PathVariable("id") int id)
	{
    	return myPersonService.findPerson(id);    	
    }

	@GetMapping("")
	public ArrayList<Person> getAllPersons(){
		return myPersonService.getAllPersons();
	}
	
	@GetMapping("/findbycompname")
	public ArrayList<Person> findPersonByCompany(@RequestParam("compname") String compName)
	{
		return myPersonService.findPersonByCompany(compName);				
	}
	
	@PostMapping("/login")
    public Person findPersonByMailAndPassWord(@RequestBody Login login)
	{
    	System.out.println("emailaddress: " +login.getEmailAddress());
    	System.out.println("password: " +login.getPassWord());
		return myPersonService.findPerson(login.getEmailAddress(), login.getPassWord());    	
    }
	
	@PostMapping("")
    public void addPerson(@RequestBody Person person) throws Exception 
	{
		myPersonService.addPerson(person);
    }
	
	@DeleteMapping("{id}")
    public void deletePerson(@PathVariable("id") int id) throws Exception 
	{
		myPersonService.deletePerson(id);
    }	
	
	@PutMapping("{id}")
    public void changePassword(@PathVariable("id") int id, @RequestBody Person person) throws IOException  
	{
		myPersonService.changePassword(myPersonService.findPerson(id), person.getPassword());
    }
}
