package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.bean.Person;
import com.safetynet.alerts.model.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/person")
public class PersonController
{
	PersonService personService = new PersonService();
	@RequestMapping(method = RequestMethod.GET)
	public ArrayList<Person> getPersons()
	{
		return personService.getPersons();
	}
	@RequestMapping(method = RequestMethod.POST)
	public void postPerson(@RequestBody Person person)
	{
		personService.createPerson(person);
	}
	@RequestMapping(method = RequestMethod.PUT)
	public void putPerson(@RequestBody Person person)
	{
		personService.modifyPerson(person);
	}
	@RequestMapping(method = RequestMethod.DELETE)
	public void deletePerson(@RequestBody Person person)
	{
		personService.deletePerson(person);
	}
}
