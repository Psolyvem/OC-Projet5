package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.bean.Firestation;
import com.safetynet.alerts.model.bean.Person;
import com.safetynet.alerts.model.service.FirestationService;
import com.safetynet.alerts.model.service.JsonReader;
import com.safetynet.alerts.model.service.MedicalRecordService;
import com.safetynet.alerts.model.service.PersonService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@RestController
@RequestMapping("/firestation")
public class FirestationController
{
	FirestationService firestationService = new FirestationService();

	@RequestMapping(method = RequestMethod.GET, params = "!stationNumber")
	public ArrayList<Firestation> getFirestations(@RequestParam(name = "stationNumber", defaultValue = "", required = false) Integer stationNumber)
	{
		return firestationService.getFirestations();
	}

	@RequestMapping(method = RequestMethod.GET, params = "stationNumber")
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

	@RequestMapping(method = RequestMethod.POST)
	public void postFirestation(@RequestBody Firestation firestation)
	{
		firestationService.createFirestation(firestation);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public void putFirestation(@RequestBody Firestation firestation)
	{
		firestationService.modifyFirestation(firestation);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteFirestation(@RequestBody Firestation firestation)
	{
		firestationService.deleteFirestation(firestation);
	}
}
