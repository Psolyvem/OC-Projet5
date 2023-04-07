package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Firestation;
import com.safetynet.alerts.model.bean.MedicalRecord;
import com.safetynet.alerts.model.bean.Person;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

@Data
@Component
public class JsonReader
{
	private static final Logger logger = LogManager.getLogger();
	public static ArrayList<Person> readPersons(String filename)
	{
		logger.info("Reading persons from data.json");
		ArrayList<Person> persons = new ArrayList<>();
		try
		{
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonFile = (JSONObject) jsonParser.parse(new FileReader(filename));
			JSONArray jsonPersons = (JSONArray) jsonFile.get("persons");

			for (int i = 0; i < jsonPersons.size(); i++)
			{
				JSONObject jsonPerson = (JSONObject) jsonPersons.get(i);
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

	public static ArrayList<Firestation> readFirestations(String filename)
	{
		logger.info("Reading fire stations from data.json");
		ArrayList<Firestation> firestations = new ArrayList<>();
		try
		{
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonFile = (JSONObject) jsonParser.parse(new FileReader(filename));
			JSONArray jsonFirestations = (JSONArray) jsonFile.get("firestations");

			for (int i = 0; i < jsonFirestations.size(); i++)
			{
				JSONObject jsonFirestation = (JSONObject) jsonFirestations.get(i);
				Firestation firestation = new Firestation();
				firestation.setAddress((String) jsonFirestation.get("address"));
				firestation.setStation(parseInt((String) jsonFirestation.get("station")));
				firestations.add(firestation);
			}

		} catch (IOException e)
		{
			logger.error("Unable to open json file");
		} catch (ParseException e)
		{
			logger.error("Error parsing json file");
		}

		return firestations;
	}

	public static ArrayList<MedicalRecord> readMedicalRecords(String filename)
	{
		logger.info("Reading medical records from data.json");
		ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
		try
		{
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonFile = (JSONObject) jsonParser.parse(new FileReader(filename));
			JSONArray jsonMedicalRecords = (JSONArray) jsonFile.get("medicalrecords");

			for (int i = 0; i < jsonMedicalRecords.size(); i++)
			{
				JSONObject jsonMedicalRecord = (JSONObject) jsonMedicalRecords.get(i);
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

		} catch (IOException e)
		{
			logger.error("Unable to open json file");
		} catch (ParseException e)
		{
			logger.error("Error parsing json file");
		}

		return medicalRecords;
	}
}
