package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.bean.Person;
import com.safetynet.alerts.model.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController("/person")
public class PersonController
{
	PersonService personService = new PersonService();
	@GetMapping("/person")
	public ArrayList<Person> getPerson()
	{
		return personService.getPersons();
	}
	@PostMapping("/person")
	public void postPerson()
	{

	}
	@PutMapping("/person")
	public void putPerson()
	{

	}
	@DeleteMapping("/person")
	public void deletePerson()
	{

	}
}
