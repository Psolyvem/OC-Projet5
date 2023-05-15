package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.bean.Person;
import com.safetynet.alerts.model.service.JsonReader;
import com.safetynet.alerts.model.service.MedicalRecordService;
import com.safetynet.alerts.model.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/childAlert")
@SuppressWarnings("unchecked")
public class ChildAlertController
{
	private static final Logger logger = LogManager.getLogger();

	@RequestMapping(method = RequestMethod.GET, params = "address")
	public JSONObject getChildAlert(@RequestParam(name = "address", defaultValue = "") String address)
	{
		JSONObject response = new JSONObject();
		JSONArray jsonPersons = new JSONArray();
		PersonService personService = new PersonService();

		for (Person person : personService.getPersons())
		{
			long age = 0;
			if (JsonReader.getInstance().jsonDateToJavaDate(new MedicalRecordService().getMedicalRecordByName(person.getFirstName(), person.getLastName()).getBirthDate()) != null)
			{
				LocalDate birthDate = JsonReader.getInstance().jsonDateToJavaDate(new MedicalRecordService().getMedicalRecordByName(person.getFirstName(), person.getLastName()).getBirthDate());
				LocalDate now = LocalDate.now();
				age = ChronoUnit.YEARS.between(birthDate, now);
			}

			if (person.getAddress().equals(address) && age < 18)
			{
				JSONObject jsonPerson = new JSONObject();
				jsonPerson.put("firstName", person.getFirstName());
				jsonPerson.put("lastName", person.getLastName());
				jsonPerson.put("age", age);
				JSONArray personsInHome = new JSONArray();
				for (Person person2 : personService.getPersons())
				{
					if (person2.getAddress().equals(address))
					{
						personsInHome.add(person2);
					}
				}
				jsonPerson.put("persons in home", personsInHome);
				jsonPersons.add(jsonPerson);
			}
		}
		if (!jsonPersons.isEmpty())
		{
			response.put("children", jsonPersons);
			return response;
		}
		else {
			return null;
		}



	}
}
