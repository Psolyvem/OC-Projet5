package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.DataConfig;
import com.safetynet.alerts.model.bean.Firestation;
import com.safetynet.alerts.model.bean.MedicalRecord;
import lombok.Data;
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

import static java.lang.Integer.parseInt;

@Data
@Component
class JsonReader
{
	private static final Logger logger = LogManager.getLogger();

	/**
	 * @return The data in the data.json file as a JSONObject
	 */
	protected static JSONObject getData()
	{
		logger.info("Reading data from data.json");
		JSONObject jsonFile = new JSONObject();
		try
		{
			JSONParser jsonParser = new JSONParser();
			jsonFile = (JSONObject) jsonParser.parse(new FileReader(DataConfig.DATASOURCE));

		} catch (IOException e)
		{
			logger.error("Unable to open json file");
		} catch (ParseException e)
		{
			logger.error("Error parsing json file");
		}

		return jsonFile;
	}

	protected static JSONArray getPersons()
	{
		return (JSONArray) getData().get("persons");
	}

	protected static void writePerson(JSONObject person)
	{
		JSONArray persons = getPersons();
		persons.add(person);
		writePersons(persons);
	}
	private static void writePersons(JSONArray persons)
	{
		JSONObject data = getData();
		data.replace("persons", persons);
		writeFile(data);
	}

	private static void writeFile(JSONObject data)
	{
		try
		{
			FileWriter writer = new FileWriter(DataConfig.DATASOURCE, false);
			writer.write(data.toJSONString());
			writer.close();
		} catch (IOException e)
		{
			logger.error("Unable to write in json file");
		}
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

			for (Object o : jsonFirestations)
			{
				JSONObject jsonFirestation = (JSONObject) o;
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
