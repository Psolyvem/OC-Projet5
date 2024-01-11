package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.JsonData;
import com.safetynet.alerts.model.bean.Person;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;

@Service
public class PersonService implements IPersonService
{
	/**
	 * Get all the persons from the file
	 *
	 */
	@Override
	public ArrayList<Person> getPersons()
	{
		return JsonReader.getInstance().readData().getPersons();
	}

	/**
	 * Get a person from the file
	 *
	 * @param firstName first name of the person
	 * @param lastName last name of the person
	 */
	@Override
	public Person getPersonByName(String firstName, String lastName)
	{
		ArrayList<Person> persons = getPersons();
		for (Person person : persons)
		{
			if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
			{
				Logger.info("Getting " + person.getFirstName() + " " + person.getLastName());
				return person;
			}
		}

		Logger.info("Person not found");
		return null;
	}

	/**
	 * Add a person to the list and writes it
	 * Check if the person already exist;
	 *
	 * @param person the person to add
	 */
	@Override
	public void createPerson(Person person)
	{
		if (person == null)
		{
			Logger.info("No person provided");
			return;
		}

		JsonData data = JsonReader.getInstance().readData();
		ArrayList<Person> persons = data.getPersons();
		for (Person person1 : persons)
		{
			if (person1.getFirstName().equals(person.getFirstName()) && person1.getLastName().equals(person.getLastName()))
			{
				Logger.error("Unable to create person : already exist");
				return;
			}
		}

		Logger.info("Adding " + person.getFirstName() + " " + person.getLastName());
		persons.add(person);
		data.setPersons(persons);
		JsonReader.getInstance().setData(data);
		JsonReader.getInstance().writeData();

	}

	/**
	 * Modify a person and rewrite the file
	 *
	 * @param person the person to modify
	 */
	@Override
	public void modifyPerson(Person person)
	{
		if (person == null)
		{
			Logger.info("No person provided");
			return;
		}

		JsonData data = JsonReader.getInstance().readData();
		ArrayList<Person> persons = data.getPersons();
		Person oldPerson = null;
		for (Person person1 : persons)
		{
			if (person1.getFirstName().equals(person.getFirstName()) && person1.getLastName().equals(person.getLastName()))
			{
				oldPerson = person1;
			}
		}

		if (oldPerson == null)
		{
			Logger.error("Unable to modify person : does not exist");
		}
		else
		{
			Logger.info("Modifying " + person.getFirstName() + " " + person.getLastName());
			persons.remove(oldPerson);
			persons.add(person);
			data.setPersons(persons);
			JsonReader.getInstance().setData(data);
			JsonReader.getInstance().writeData();
		}
	}

	/**
	 * Delete a person and rewrite the file
	 *
	 * @param person the person to delete
	 */
	@Override
	public void deletePerson(Person person)
	{
		if (person == null)
		{
			Logger.info("No person provided");
			return;
		}

		JsonData data = JsonReader.getInstance().readData();
		ArrayList<Person> persons = data.getPersons();
		boolean alreadyExists = false;
		for (Person person1 : persons)
		{
			if (person1.getFirstName().equals(person.getFirstName()) && person1.getLastName().equals(person.getLastName()))
			{
				alreadyExists = true;
				break;
			}
		}
		if (!alreadyExists)
		{
			Logger.error("Unable to delete person : does not exist");
		}
		else
		{
			Logger.info("Deleting " + person.getFirstName() + " " + person.getLastName());
			persons.remove(person);
			data.setPersons(persons);
			JsonReader.getInstance().setData(data);
			JsonReader.getInstance().writeData();
		}
	}

}
