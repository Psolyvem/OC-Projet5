package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.*;
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

/**
 * Singleton that read and write Json Data
 */
@Component
class JsonReader
{
	private static final Logger logger = LogManager.getLogger();
	private static JsonReader instance;
	private JsonData data;

	private JsonReader()
	{
		data = new JsonData();
		readData();
	}

	public static JsonReader getInstance()
	{
		if (instance == null)
		{
			instance = new JsonReader();
		}
		return instance;
	}

	/**
	 * @return The data in the data.json file as a JsonData object
	 */
	protected JsonData readData()
	{
		logger.info("Reading data from data.json");

		try
		{
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonFile = (JSONObject) jsonParser.parse(new FileReader(DataConfig.DATASOURCE));

			// Matching persons to Java objects
			JSONArray jsonPersons = (JSONArray) jsonFile.get("persons");
			ArrayList<Person> persons = new ArrayList<>();
			if (jsonPersons == null)
			{
				logger.info("No data found for : persons");
			}
			else
			{
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
			}


			// Matching firestations to Java objects
			JSONArray jsonFirestations = (JSONArray) jsonFile.get("firestations");
			ArrayList<Firestation> firestations = new ArrayList<>();
			if (jsonFirestations == null)
			{
				logger.info("No data found for : firestations");
			}
			else
			{
				for (Object obj : jsonFirestations)
				{
					JSONObject jsonFirestation = (JSONObject) obj;
					Firestation firestation = new Firestation();
					firestation.setAddress((String) jsonFirestation.get("address"));
					firestation.setStation((String) jsonFirestation.get("station"));
					firestations.add(firestation);
				}
			}

			// Matching medical records to Java objects
			JSONArray jsonMedicalRecords = (JSONArray) jsonFile.get("medicalrecords");
			ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
			if (jsonMedicalRecords == null)
			{
				logger.info("No data found for : medical records");
			}
			else
			{
				for (Object record : jsonMedicalRecords)
				{
					JSONObject jsonMedicalRecord = (JSONObject) record;
					MedicalRecord medicalRecord = new MedicalRecord();
					medicalRecord.setFirstName((String) jsonMedicalRecord.get("firstName"));
					medicalRecord.setLastName((String) jsonMedicalRecord.get("lastName"));
					medicalRecord.setBirthDate((String) jsonMedicalRecord.get("birthdate"));

					JSONArray jsonMedications = (JSONArray) jsonMedicalRecord.get("medications");
					ArrayList<String> medications = new ArrayList<>();
					for (Object obj : jsonMedications)
					{
						medications.add((String) obj);
					}

					JSONArray jsonAllergies = (JSONArray) jsonMedicalRecord.get("allergies");
					ArrayList<String> allergies = new ArrayList<>();
					for (Object obj : jsonAllergies)
					{
						allergies.add((String) obj);
					}

					medicalRecord.setMedications(medications);
					medicalRecord.setAllergies(allergies);
					medicalRecords.add(medicalRecord);
				}
			}

			// Saving it in JsonData
			data.setPersons(persons);
			data.setFirestations(firestations);
			data.setMedicalRecords(medicalRecords);

		} catch (IOException e)
		{
			logger.error("Unable to open json file");
		} catch (ParseException e)
		{
			logger.error("Error parsing json file");
		}

		return data;
	}

	private void writeData()
	{
		logger.info("Writing data to data.json");
		JSONObject jsonFile = new JSONObject();

		// Mapping Java objects to Json data
		JSONArray jsonPersons = new JSONArray();
		ArrayList<Person> persons = data.getPersons();
		for (Person person : persons)
		{
			JSONObject jsonPerson = new JSONObject();
			jsonPerson.put("firstName", person.getFirstName());
			jsonPerson.put("lastName", person.getLastName());
			jsonPerson.put("address", person.getAddress());
			jsonPerson.put("city", person.getCity());
			jsonPerson.put("zip", person.getZip());
			jsonPerson.put("email", person.getEmail());
			jsonPerson.put("phone", person.getPhone());
			jsonPersons.add(jsonPerson);
		}

		JSONArray jsonFirestations = new JSONArray();
		ArrayList<Firestation> firestations = data.getFirestations();
		for (Firestation firestation : firestations)
		{
			JSONObject jsonFirestation = new JSONObject();
			jsonFirestation.put("address", firestation.getAddress());
			jsonFirestation.put("station", firestation.getStation());
			jsonFirestations.add(jsonFirestation);
		}

		JSONArray jsonMedicalRecords = new JSONArray();
		ArrayList<MedicalRecord> medicalRecords = data.getMedicalRecords();
		for (MedicalRecord medicalRecord : medicalRecords)
		{
			JSONObject jsonMedicalRecord = new JSONObject();
			jsonMedicalRecord.put("firstName", medicalRecord.getFirstName());
			jsonMedicalRecord.put("lastName", medicalRecord.getLastName());
			jsonMedicalRecord.put("birthdate", medicalRecord.getBirthDate());
			JSONArray jsonMedications = new JSONArray();
			ArrayList<String> medications = medicalRecord.getMedications();
			for (String medication : medications)
			{
				jsonMedications.add(medication);
			}
			jsonMedicalRecord.put("medications", jsonMedications);

			JSONArray jsonAllergies = new JSONArray();
			ArrayList<String> allergies = medicalRecord.getAllergies();
			for (String allergie : allergies)
			{
				jsonAllergies.add(allergie);
			}
			jsonMedicalRecord.put("allergies", jsonAllergies);
			jsonMedicalRecords.add(jsonMedicalRecord);
		}

		jsonFile.put("persons", jsonPersons);
		jsonFile.put("firestations", jsonFirestations);
		jsonFile.put("medicalrecords", jsonMedicalRecords);

		// Writing the data.json file
		try
		{
			FileWriter writer = new FileWriter(DataConfig.DATASOURCE, false);
			writer.write(jsonFile.toJSONString().replace("\\/", "/"));
			writer.close();
		} catch (IOException e)
		{
			logger.error("Unable to write in json file");
		}
	}

	protected ArrayList<Person> getPersons()
	{
		readData();
		return data.getPersons();
	}

	/**
	 * Add a person to the list and writes it
	 * Does not check if the person already exist, but check if null;
	 *
	 * @param person the person to add
	 */
	protected void addPerson(Person person)
	{
		if (person.equals(null))
		{
			logger.info("No person provided");
			return;
		}
		ArrayList<Person> persons = data.getPersons();
		persons.add(person);
		data.setPersons(persons);
		writeData();
	}

	protected void deletePerson(Person person)
	{
		if (person == null)
		{
			logger.info("No person provided");
			return;
		}
		ArrayList<Person> persons = data.getPersons();
		persons.remove(person);
		data.setPersons(persons);
		writeData();
	}

	public ArrayList<Firestation> getFirestations()
	{
		readData();
		return data.getFirestations();
	}

	public ArrayList<MedicalRecord> getMedicalRecords()
	{
		readData();
		return data.getMedicalRecords();
	}
}