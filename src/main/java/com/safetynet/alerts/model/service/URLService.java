package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Firestation;
import com.safetynet.alerts.model.bean.JsonData;
import com.safetynet.alerts.model.bean.MedicalRecord;
import com.safetynet.alerts.model.bean.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class URLService implements IURLService
{

	/**
	 * @param stationNumber Numéro de la station
	 * @return Une liste des personnes couvertes par la fire station. Elle inclut prénom, nom, adresse, numéro de téléphone ainsi qu'un décompte du nombre d'adultes et du nombre d'enfants (tout individu âgé de 18 ans ou
	 * moins)
	 */
	@Override
	public JSONObject getFirestationCoverage(Integer stationNumber)
	{
		JSONObject response = new JSONObject();
		JSONArray jsonPersons = new JSONArray();
		int numberOfAdults = 0;
		int numberOfChildren = 0;

		JsonData data = JsonReader.getInstance().getData();

		for (Firestation firestation : data.getFirestations())
		{
			if (firestation.getStation().equals(stationNumber.toString()))
			{
				for (Person person : data.getPersons())
				{
					if (person.getAddress().equals(firestation.getAddress()))
					{
						JSONObject jsonPerson = new JSONObject();
						jsonPerson.put("firstName", person.getFirstName());
						jsonPerson.put("lastName", person.getLastName());
						jsonPerson.put("address", person.getAddress());
						jsonPerson.put("phone", person.getPhone());
						jsonPersons.add(jsonPerson);

						LocalDate birthDate = null;

						for (MedicalRecord medicalRecord : data.getMedicalRecords())
						{
							if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName()))
							{
								birthDate = JsonReader.getInstance().jsonDateToJavaDate(medicalRecord.getBirthDate());
							}
						}

						if (birthDate == null)
						{
							Logger.error("No medical record for this person");
						}
						else
						{
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
		}
		response.put("persons", jsonPersons);
		response.put("adults", numberOfAdults);
		response.put("children", numberOfChildren);

		return response;
	}

	/**
	 * @param address l'adresse de recherche
	 * @return La liste des enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse. Elle inclut le prénom et le nom de famille de chaque enfant, son âge et une liste des autres
	 * membres du foyer.
	 */
	@Override
	public JSONObject getChildAlert(String address)
	{
		JSONObject response = new JSONObject();
		JSONArray jsonChildren = new JSONArray();
		JSONArray jsonAdults = new JSONArray();
		JsonData data = JsonReader.getInstance().getData();

		for (Person person : data.getPersons())
		{
			if (person.getAddress().equals(address))
			{
				long age = 0;
				LocalDate birthDate = null;

				for (MedicalRecord medicalRecord : data.getMedicalRecords())
				{
					if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName()))
					{
						birthDate = JsonReader.getInstance().jsonDateToJavaDate(medicalRecord.getBirthDate());
					}
				}
				LocalDate now = LocalDate.now();
				age = ChronoUnit.YEARS.between(birthDate, now);

				if (age < 18)
				{
					JSONObject jsonPerson = new JSONObject();
					jsonPerson.put("firstName", person.getFirstName());
					jsonPerson.put("lastName", person.getLastName());
					jsonPerson.put("age", "" + age);
					jsonChildren.add(jsonPerson);
				}
				else
				{
					JSONObject jsonPerson = new JSONObject();
					jsonPerson.put("firstName", person.getFirstName());
					jsonPerson.put("lastName", person.getLastName());
					jsonPerson.put("age", "" + age);
					jsonAdults.add(jsonPerson);
				}
			}
		}
		if (!jsonChildren.isEmpty())
		{
			response.put("children", jsonChildren);
			response.put("adults", jsonAdults);
			return response;
		}
		else
		{
			return null;
		}
	}

	/**
	 * @param firestationNumber le numéro de la station
	 * @return La liste des numéros de téléphone des résidents desservis par la station
	 */
	@Override
	public JSONObject getPhoneAlert(Integer firestationNumber)
	{
		JSONObject response = new JSONObject();
		JSONArray phones = new JSONArray();
		JsonData data = JsonReader.getInstance().getData();

		for (Firestation firestation : data.getFirestations())
		{
			if (firestation.getStation().equals(firestationNumber.toString()))
			{
				for (Person person : data.getPersons())
				{
					if (person.getAddress().equals(firestation.getAddress()))
					{
						if (!phones.contains(person.getPhone()))
						{
							phones.add(person.getPhone());
						}
					}
				}
			}
		}
		response.put("phonenumbers", phones);

		return response;
	}

	/**
	 * @param address l'adresse de recherche
	 * @return La liste des habitants vivant à l’adresse donnée ainsi que le numéro de la station la desservant. Elle inclut le nom, le numéro de téléphone, l'âge et les antécédents
	 * médicaux (médicaments, posologie et allergies)
	 */
	@Override
	public JSONObject getFireAddress(String address)
	{
		JSONObject response = new JSONObject();
		JSONArray persons = new JSONArray();
		JsonData data = JsonReader.getInstance().getData();

		for (Firestation firestation : data.getFirestations())
		{
			if (firestation.getAddress().equals(address))
			{
				response.put("firestationNumber", firestation.getStation());
			}
		}
		for (Person person : data.getPersons())
		{
			if (person.getAddress().equals(address))
			{
				JSONObject jsonPerson = new JSONObject();
				jsonPerson.put("firstName", person.getFirstName());
				jsonPerson.put("lastName", person.getLastName());
				jsonPerson.put("phone", person.getPhone());

				long age = 0;
				LocalDate birthDate = null;

				for (MedicalRecord medicalRecord : data.getMedicalRecords())
				{
					if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName()))
					{
						birthDate = JsonReader.getInstance().jsonDateToJavaDate(medicalRecord.getBirthDate());
					}
				}
				LocalDate now = LocalDate.now();
				age = ChronoUnit.YEARS.between(birthDate, now);

				jsonPerson.put("age", age);

				for (MedicalRecord medicalRecord : data.getMedicalRecords())
				{
					if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName()))
					{
						jsonPerson.put("medications", medicalRecord.getMedications());
						jsonPerson.put("allergies", medicalRecord.getAllergies());
					}
				}

				persons.add(jsonPerson);
			}
		}
		response.put("persons", persons);
		return response;
	}

	/**
	 * @param stations la liste des stations concernées
	 * @return La liste de tous les foyers desservis par les stations, groupées par station. Elle inclut le nom, le numéro de téléphone et l'âge des habitants, et
	 * faire figurer leurs antécédents médicaux (médicaments, posologie et allergies)
	 */
	@Override
	public JSONObject getFloodAlert(List<String> stations)
	{
		JSONObject response = new JSONObject();
		JsonData data = JsonReader.getInstance().getData();

		for (String station : stations)
		{
			JSONObject jsonStation = new JSONObject();
			for (Firestation firestation : data.getFirestations())
			{
				if (firestation.getStation().equals(station))
				{
					JSONArray jsonPersons = new JSONArray();
					for (Person person : data.getPersons())
					{
						if (person.getAddress().equals(firestation.getAddress()))
						{
							JSONObject jsonPerson = new JSONObject();
							jsonPerson.put("firstName", person.getFirstName());
							jsonPerson.put("lastName", person.getLastName());
							jsonPerson.put("phone", person.getPhone());

							long age = 0;
							LocalDate birthDate = null;

							for (MedicalRecord medicalRecord : data.getMedicalRecords())
							{
								if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName()))
								{
									birthDate = JsonReader.getInstance().jsonDateToJavaDate(medicalRecord.getBirthDate());
								}
							}
							LocalDate now = LocalDate.now();
							age = ChronoUnit.YEARS.between(birthDate, now);

							jsonPerson.put("age", age);

							for (MedicalRecord medicalRecord : data.getMedicalRecords())
							{
								if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName()))
								{
									jsonPerson.put("medications", medicalRecord.getMedications());
									jsonPerson.put("allergies", medicalRecord.getAllergies());
								}
							}
							jsonPersons.add(jsonPerson);
						}
					}
					jsonStation.put(firestation.getAddress(), jsonPersons);
				}
			}
			response.put(station, jsonStation);
		}
		return response;
	}

	/**
	 * @param firstName le prénom de la personne recherchée
	 * @param lastName le nom de famille de la personne recherchée
	 * @return Les informations sur une personne. Elle inclut le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
	 * posologie, allergies)
	 */
	@Override
	public JSONObject getPersonInfo(String firstName, String lastName)
	{
		JSONObject response = new JSONObject();
		JSONArray jsonPersons = new JSONArray();
		JsonData data = JsonReader.getInstance().getData();

		for (Person person : data.getPersons())
		{
			if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
			{
				JSONObject jsonPerson = new JSONObject();
				jsonPerson.put("firstName", person.getFirstName());
				jsonPerson.put("lastName", person.getLastName());

				long age = 0;
				LocalDate birthDate = null;

				for (MedicalRecord medicalRecord : data.getMedicalRecords())
				{
					if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName()))
					{
						birthDate = JsonReader.getInstance().jsonDateToJavaDate(medicalRecord.getBirthDate());
					}
				}
				LocalDate now = LocalDate.now();
				age = ChronoUnit.YEARS.between(birthDate, now);

				jsonPerson.put("age", age);
				jsonPerson.put("email", person.getEmail());

				for (MedicalRecord medicalRecord : data.getMedicalRecords())
				{
					if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName()))
					{
						jsonPerson.put("medications", medicalRecord.getMedications());
						jsonPerson.put("allergies", medicalRecord.getAllergies());
					}
				}
				jsonPersons.add(jsonPerson);
			}
		}
		response.put("persons", jsonPersons);
		return response;
	}

	/**
	 * @param city la ville recherchée
	 * @return Les adresses mail de tous les habitants de la ville.
	 */
	@Override
	public JSONObject getCommunityEmail(String city)
	{
		JSONObject response = new JSONObject();
		JSONArray jsonPersons = new JSONArray();
		JsonData data = JsonReader.getInstance().getData();

		for(Person person : data.getPersons())
		{
			if(person.getCity().equals(city))
			{
				jsonPersons.add(person.getEmail());
			}
		}
		response.put("emails", jsonPersons);
		return response;
	}
}
