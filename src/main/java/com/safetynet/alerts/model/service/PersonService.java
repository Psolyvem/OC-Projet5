package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PersonService implements IPersonService
{
	private static final Logger logger = LogManager.getLogger();

	public ArrayList<Person> getPersons()
	{
		return JsonReader.getInstance().getPersons();
	}

	public Person getPersonByName(String firstName, String lastName)
	{
		ArrayList<Person> persons = getPersons();
		for (Person person : persons)
		{
			if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
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
			JsonReader.getInstance().addPerson(person);
		}
	}

	public void modifyPerson(Person person)
	{
		if (getPersonByName(person.getFirstName(), person.getLastName()) == null)
		{
			logger.error("Unable to modify person : does not exist");
		}
		else
		{
			JsonReader.getInstance().deletePerson(getPersonByName(person.getFirstName(), person.getLastName()));
			JsonReader.getInstance().addPerson(person);
		}
	}

	public void deletePerson(Person person)
	{
		JsonReader.getInstance().deletePerson(person);
	}

}
