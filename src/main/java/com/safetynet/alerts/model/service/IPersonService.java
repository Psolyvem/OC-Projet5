package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Person;

import java.util.ArrayList;

public interface IPersonService
{
	public ArrayList<Person> getPersons();
	public Person getPersonByName(String firstName, String lastName);
	public void createPerson(Person person);
	public void modifyPerson(Person person);
	public void deletePerson(Person person);
}
