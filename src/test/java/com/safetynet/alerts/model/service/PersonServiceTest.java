package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersonServiceTest
{
	@Test
	public void createPersonTest()
	{
		PersonService personService = new PersonService();
		Person person = new Person();
		person.setFirstName("Jean");
		person.setLastName("Bon");
		person.setAddress("35 rue gudu");
		person.setCity("Ecouflant");
		person.setZip("49000");
		person.setEmail("jean.bon@gudu.freu");
		person.setPhone("0123456789");
		personService.createPerson(person);

		assertEquals(person, personService.getPersonByName("Jean", "Bon"));
	}
}
