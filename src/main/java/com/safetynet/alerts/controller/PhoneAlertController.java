package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.bean.Firestation;
import com.safetynet.alerts.model.bean.Person;
import com.safetynet.alerts.model.service.FirestationService;
import com.safetynet.alerts.model.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phoneAlert")
@SuppressWarnings("unchecked")
public class PhoneAlertController
{
	private static final Logger logger = LogManager.getLogger();

	@RequestMapping(method = RequestMethod.GET, params = "firestation")
	public JSONObject getPhoneNumberByStation(@RequestParam(name = "firestation", defaultValue = "") Integer firestationNumber)
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
}
