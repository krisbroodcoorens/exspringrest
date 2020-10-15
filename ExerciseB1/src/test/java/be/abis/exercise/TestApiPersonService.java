package be.abis.exercise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import be.abis.exercise.model.Login;
import be.abis.exercise.model.Person;
import be.abis.exercise.service.ApiPersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApiPersonService 
{
	@Autowired
	ApiPersonService myApiPersonService;
	
	@Test
	public void testApiServiceFindPersonById() 
	{
		int searchId = 1;
		String firstName ="John";
		Person myPerson = myApiPersonService.findPerson(searchId);		
		assertEquals(myPerson.getFirstName(), firstName);
	}
	
	@Test
	public void testApiServiceLogin() 
	{
		Login newLogin = new Login("jdoe@abis.be", "abc123");
		assertNotNull(myApiPersonService.findPersonByLogin(newLogin));
	}
}
