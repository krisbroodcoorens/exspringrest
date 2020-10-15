package be.abis.exercise;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

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
}
