package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Person;

import java.util.ArrayList;

public interface IPersonService
{
	ArrayList<Person> getPersons();
	Person getPersonByName(String firstName, String lastName);
	void createPerson(Person person);
	void modifyPerson(Person person);
	void deletePerson(String name);
}
