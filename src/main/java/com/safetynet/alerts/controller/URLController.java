package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.bean.Firestation;
import com.safetynet.alerts.model.bean.Person;
import com.safetynet.alerts.model.service.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@SuppressWarnings("unchecked")
public class URLController
{
	@Autowired
	IURLService urlService;

	//TODO
	@GetMapping(path = "/firestation", params = "stationNumber")
	public JSONObject getFirestationCoverage(@RequestParam(name = "stationNumber", defaultValue = "0") Integer stationNumber)
	{
		JSONObject response = new JSONObject();
		JSONArray jsonPersons = new JSONArray();
		int numberOfAdults = 0;
		int numberOfChildren = 0;

		for (Firestation firestation : firestationService.getFirestations())
		{
			if(firestation.getStation().equals(stationNumber.toString()))
			{
				for(Person person : new PersonService().getPersons())
				{
					if (person.getAddress().equals(firestation.getAddress()))
					{
						JSONObject jsonPerson = new JSONObject();
						jsonPerson.put("firstName", person.getFirstName());
						jsonPerson.put("lastName", person.getLastName());
						jsonPerson.put("address", person.getAddress());
						jsonPerson.put("phone", person.getPhone());
						jsonPersons.add(jsonPerson);

						LocalDate birthDate = JsonReader.getInstance().jsonDateToJavaDate(new MedicalRecordService().getMedicalRecordByName(person.getFirstName(), person.getLastName()).getBirthDate());
						LocalDate now = LocalDate.now();
						if (ChronoUnit.YEARS.between(birthDate, now) < 18)
						{
							numberOfChildren++;
						}
						else
						{
							numberOfAdults++;
						}
					}
				}
			}
		}
		response.put("persons", jsonPersons);
		response.put("adults", numberOfAdults);
		response.put("children", numberOfChildren);

		return response;
	}

	//TODO
	@GetMapping(path = "/childAlert", params = "address")
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
		else
		{
			return null;
		}
	}

	//TODO
	@GetMapping(path = "/phoneAlert", params = "firestation")
	public JSONObject getPhoneAlert(@RequestParam(name = "firestation", defaultValue = "") Integer firestationNumber)
	{
		JSONObject response = new JSONObject();
		JSONArray phones = new JSONArray();
		FirestationService firestationService = new FirestationService();
		PersonService personService = new PersonService();

		for (Firestation firestation : firestationService.getFirestations())
		{
			if (firestation.getStation().equals(firestationNumber.toString()))
			{
				for (Person person : personService.getPersons())
				{
					if (person.getAddress().equals(firestation.getAddress()))
					{
						phones.add(person.getPhone());
					}
				}
			}
		}
		response.put("phonenumbers", phones);

		return response;
	}

	//TODO
	@GetMapping(path = "/fire", params = "address")
	public JSONObject getFireAddress(@RequestParam(name="address", defaultValue = "") String address)
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

	//TODO
	@GetMapping(path = "/flood/stations", params = "stations")
	public JSONObject getFloodAlert(@RequestParam(name = "stations", defaultValue = "") List<String> stations)
	{
		return ;
	}

	//TODO
	@GetMapping(path = "/personInfo", params = {"firstName", "lastName"})
	public JSONObject getPersonInfo(@RequestParam(name = "firstName", defaultValue = "") String firstName, @RequestParam(name = "lastName", defaultValue = "") String lastName)
	{
		return ;
	}

	//TODO
	@GetMapping(path = "/communityEmail", params = "city")
	public JSONObject getCommunityEmail(@RequestParam(name = "city", defaultValue = "") String city)
	{
		return ;
	}
}
