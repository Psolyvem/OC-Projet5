package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.bean.Firestation;
import com.safetynet.alerts.model.bean.Person;
import com.safetynet.alerts.model.service.FirestationService;
import com.safetynet.alerts.model.service.JsonReader;
import com.safetynet.alerts.model.service.MedicalRecordService;
import com.safetynet.alerts.model.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/fire")
@SuppressWarnings("unchecked")
public class FireController
{
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	PersonService personService;
	@Autowired
	FirestationService firestationService;
	@Autowired
	MedicalRecordService medicalRecordService;
	@RequestMapping(method = RequestMethod.GET, params = "address")
	public JSONObject getInfoFromAddress(@RequestParam(name="address", defaultValue = "") String address)
	{
		JSONObject response = new JSONObject();
		JSONArray persons = new JSONArray();

		for (Firestation firestation : firestationService.getFirestations())
		{
			if(firestation.getAddress().equals(address))
			{
				response.put("firestationnumber", firestation.getStation());
			}
		}
		for (Person person : personService.getPersons())
		{
			if (person.getAddress().equals(address))
			{
				JSONObject jsonPerson = new JSONObject();
				jsonPerson.put("firstName", person.getFirstName());
				jsonPerson.put("lastName", person.getLastName());
				jsonPerson.put("phone", person.getPhone());

				long age = 0;
				if (JsonReader.getInstance().jsonDateToJavaDate(medicalRecordService.getMedicalRecordByName(person.getFirstName(), person.getLastName()).getBirthDate()) != null)
				{
					LocalDate birthDate = JsonReader.getInstance().jsonDateToJavaDate(medicalRecordService.getMedicalRecordByName(person.getFirstName(), person.getLastName()).getBirthDate());
					LocalDate now = LocalDate.now();
					age = ChronoUnit.YEARS.between(birthDate, now);
				}
				jsonPerson.put("age", age);
				jsonPerson.put("medications", medicalRecordService.getMedicalRecordByName(person.getFirstName(), person.getLastName()).getMedications());
				jsonPerson.put("allergies", medicalRecordService.getMedicalRecordByName(person.getFirstName(), person.getLastName()).getAllergies());
				persons.add(jsonPerson);
			}
		}
		response.put("persons", persons);
		return response;
	}
}
