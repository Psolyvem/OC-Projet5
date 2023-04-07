package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.DataConfig;
import com.safetynet.alerts.model.bean.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class PersonService
{
	private static final Logger logger = LogManager.getLogger();
	public ArrayList<Person> getPersons()
	{
		logger.info("Reading persons from data.json");
		ArrayList<Person> persons = new ArrayList<>();
		try
		{
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonFile = (JSONObject) jsonParser.parse(new FileReader(DataConfig.DATASOURCE));
			JSONArray jsonPersons = (JSONArray) jsonFile.get("persons");

			for (Object obj : jsonPersons)
			{
				JSONObject jsonPerson = (JSONObject) obj;
				Person person = new Person();
				person.setFirstName((String) jsonPerson.get("firstName"));
				person.setLastName((String) jsonPerson.get("lastName"));
				person.setAddress((String) jsonPerson.get("address"));
				person.setCity((String) jsonPerson.get("city"));
				person.setZip((String) jsonPerson.get("zip"));
				person.setEmail((String) jsonPerson.get("email"));
				person.setPhone((String) jsonPerson.get("phone"));
				persons.add(person);
			}
		} catch (IOException e)
		{
			logger.error("Unable to open json file");
		} catch (ParseException e)
		{
			logger.error("Error parsing json file");
		}

		return persons;
	}
	public Person getPersonByName(String firstName, String lastName)
	{
		ArrayList<Person> persons = getPersons();
		for (Person person : persons)
		{
			if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
			{
				return person;
			}
		}

		logger.info("Person not found");
		return null;
	}
	public void createPerson(Person person)
	{
		if (getPersonByName(person.getFirstName(), person.getLastName()) != null)
		{
			logger.error("Unable to create person : already exist");
		}
		else
		{
			JSONObject jsonPerson = new JSONObject();
			jsonPerson.put("firstName", person.getFirstName());
			jsonPerson.put("lastName", person.getLastName());
			jsonPerson.put("address", person.getAddress());
			jsonPerson.put("city", person.getCity());
			jsonPerson.put("zip", person.getZip());
			jsonPerson.put("email", person.getEmail());
			jsonPerson.put("phone", person.getPhone());
			try
			{
				FileWriter writer = new FileWriter(DataConfig.DATASOURCE, true);
				writer.write(jsonPerson.toJSONString());
				writer.close();
			}
			catch (IOException e)
			{
				logger.error("Unable to write in json file");
			}
		}
	}
	public void modifyPerson(Person person)
	{

	}
	public void deletePerson(String name)
	{

	}

}
