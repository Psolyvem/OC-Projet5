package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Person;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;

@Service
public class PersonService implements IPersonService
{
	@Override
	public ArrayList<Person> getPersons()
	{
		return JsonReader.getInstance().getPersons();
	}

	@Override
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

		Logger.info("Person not found");
		return null;
	}

	@Override
	public void createPerson(Person person)
	{
		if (getPersonByName(person.getFirstName(), person.getLastName()) != null)
		{
			Logger.error("Unable to create person : already exist");
		}
		else
		{
			JsonReader.getInstance().addPerson(person);
		}
	}

	@Override
	public void modifyPerson(Person person)
	{
		if (getPersonByName(person.getFirstName(), person.getLastName()) == null)
		{
			Logger.error("Unable to modify person : does not exist");
		}
		else
		{
			JsonReader.getInstance().deletePerson(getPersonByName(person.getFirstName(), person.getLastName()));
			JsonReader.getInstance().addPerson(person);
		}
	}

	@Override
	public void deletePerson(Person person)
	{
		JsonReader.getInstance().deletePerson(person);
	}

}
