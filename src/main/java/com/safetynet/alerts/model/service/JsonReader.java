package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Singleton that read and write Json Data<br>
 * Contains a JsonData object with the last readings of the data<br>
 * Pending changes must be added by setting the JsonData with setData(), then written in the json file with write()
 */
@Service
@SuppressWarnings("unchecked")
public class JsonReader
{
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
	public JsonData readData()
	{
		Logger.info("Reading data from data.json");

		try
		{
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonFile = (JSONObject) jsonParser.parse(new FileReader(DataConfig.DATASOURCE));

			// Matching persons to Java objects
			JSONArray jsonPersons = (JSONArray) jsonFile.get("persons");
			ArrayList<Person> persons = new ArrayList<>();
			if (jsonPersons == null)
			{
				Logger.info("No data found for : persons");
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
				Logger.info("No data found for : firestations");
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
				Logger.info("No data found for : medical records");
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
			Logger.error("Unable to open json file");
		} catch (ParseException e)
		{
			Logger.error("Error parsing json file");
		}

		return data;
	}

	public void writeData()
	{
		Logger.info("Writing data to data.json");
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
			jsonMedications.addAll(medications);
			jsonMedicalRecord.put("medications", jsonMedications);

			JSONArray jsonAllergies = new JSONArray();
			ArrayList<String> allergies = medicalRecord.getAllergies();
			jsonAllergies.addAll(allergies);
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
			Logger.error("Unable to write in json file");
		}
	}


	/**
	 * Transform a date from the format MM/dd/yyyy to a Java Date Object
	 *
	 * @param date A String in the format MM/dd/yyyy
	 * @return The date in a Java Object format
	 */
	public LocalDate jsonDateToJavaDate(String date)
	{

		int month = Integer.parseInt(date.substring(0, date.indexOf('/')));
		int day = Integer.parseInt(date.substring(date.indexOf('/') + 1, date.indexOf('/', date.indexOf('/') + 1)));
		int year = Integer.parseInt(date.substring(date.lastIndexOf('/') + 1));
		return LocalDate.of(year, month, day);

	}

	public String javaDateToJsonDate(LocalDate date)
	{
		String day, month;
		if (date.getDayOfMonth() < 10)
		{
			day = "0" + date.getDayOfMonth();
		}
		else
		{
			day = "" + date.getDayOfMonth();
		}
		if (date.getMonthValue() < 10)
		{
			month = "0" + date.getMonthValue();
		}
		else
		{
			month = "" + date.getMonthValue();
		}

		return month + "/" + day + "/" + date.getYear();
	}

	public JsonData getData()
	{
		return data;
	}

	public void setData(JsonData data)
	{
		this.data = data;
	}
}