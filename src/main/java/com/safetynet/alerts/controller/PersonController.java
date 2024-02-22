package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.bean.Person;
import com.safetynet.alerts.model.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class PersonController
{
	@Autowired
	IPersonService personService;
	@GetMapping("/person")
	public ArrayList<Person> getPersons()
	{
		return personService.getPersons();
	}
	@PostMapping("/person")
	public void postPerson(@RequestBody Person person)
	{
		personService.createPerson(person);
	}
	@PutMapping("/person")
	public void putPerson(@RequestBody Person person)
	{
		personService.modifyPerson(person);
	}
	@DeleteMapping("/person")
	public void deletePerson(@RequestBody Person person)
	{
		personService.deletePerson(person);
	}
}
