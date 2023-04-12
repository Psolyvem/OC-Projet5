package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersonServiceTest
{
	PersonService personService;

	@BeforeEach
	public void setUp()
	{
		personService = new PersonService();
	}

	@Test
	public void getPersonByNameTest()
	{
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Boyd");
		person.setAddress("1509 Culver St");
		person.setCity("Culver");
		person.setZip("97451");
		person.setEmail("jaboyd@email.com");
		person.setPhone("841-874-6512");

		assertEquals(person, personService.getPersonByName("John", "Boyd"));
	}
	@Test
	public void createPersonTest()
	{
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
