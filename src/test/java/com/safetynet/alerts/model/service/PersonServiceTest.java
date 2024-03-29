package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class PersonServiceTest
{
	@Autowired
	IPersonService personService;
	@Autowired
	Person person;

	@BeforeEach
	public void setUpEach()
	{
		person.setFirstName("Jean");
		person.setLastName("Test");
		person.setAddress("35 rue test");
		person.setCity("Ecouflant");
		person.setZip("49000");
		person.setEmail("jean.test@test.freu");
		person.setPhone("0123456789");
		personService.createPerson(person);
	}

	@AfterEach
	public void breakDown()
	{
		personService.deletePerson(personService.getPersonByName("Jean", "Test"));
	}

	@Test
	public void getPersonByNameTest()
	{
		Person person = new Person();
		person.setFirstName("Jean");
		person.setLastName("Test");
		person.setAddress("35 rue test");
		person.setCity("Ecouflant");
		person.setZip("49000");
		person.setEmail("jean.test@test.freu");
		person.setPhone("0123456789");

		assertEquals(person, personService.getPersonByName("Jean", "Test"));
	}
	@Test
	public void createPersonTest()
	{
		assertEquals(person, personService.getPersonByName("Jean", "Test"));
	}

	@Test
	public void modifyPersonTest()
	{
		person.setCity("Flers-en-Escrebieux");
		personService.modifyPerson(person);

		assertEquals("Flers-en-Escrebieux", personService.getPersonByName("Jean", "Test").getCity());
	}
	@Test
	public void deletePersonTest()
	{
		personService.deletePerson(person);

		assertNull(personService.getPersonByName("Jean", "Test"));
	}
}
