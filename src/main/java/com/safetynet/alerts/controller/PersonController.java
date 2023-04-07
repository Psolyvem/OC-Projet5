package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.bean.DataConfig;
import com.safetynet.alerts.model.service.JsonReader;
import com.safetynet.alerts.model.bean.Person;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController("/person")
public class PersonController
{
	@GetMapping("/person")
	public ArrayList<Person> getPerson()
	{
		return JsonReader.readPersons(DataConfig.DATASOURCE);
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
