package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersonServiceTest
{
	PersonService personService;
	Person person;

	@BeforeEach
	public void setUp()
	{
		personService = new PersonService();

		person = new Person();
		person.setFirstName("Jean");
		person.setLastName("Bon");
		person.setAddress("35 rue gudu");
		person.setCity("Ecouflant");
		person.setZip("49000");
		person.setEmail("jean.bon@gudu.freu");
		person.setPhone("0123456789");
		personService.createPerson(person);
	}

	@AfterEach
	public void breakDown()
	{
		personService.deletePerson(personService.getPersonByName("Jean", "Bon"));
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
		assertEquals(person, personService.getPersonByName("Jean", "Bon"));
	}

	@Test
	public void modifyPersonTest()
	{
		person.setCity("Flers-en-Escrebieux");
		personService.modifyPerson(person);

		assertEquals("Flers-en-Escrebieux", personService.getPersonByName("Jean", "Bon").getCity());
	}
	@Test
	public void deletePersonTest()
	{
		personService.deletePerson(person);

		assertEquals(null, personService.getPersonByName("Jean", "Bon"));
	}
}
