package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.JsonData;
import com.safetynet.alerts.model.bean.MedicalRecord;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;

@Service
public class MedicalRecordService implements IMedicalRecordService
{
	/**
	 * Get all the medical records from the file
	 *
	 */
	@Override
	public ArrayList<MedicalRecord> getMedicalRecords()
	{
		return JsonReader.getInstance().readData().getMedicalRecords();
	}

	/**
	 * Get the medical record of a person from the file
	 *
	 * @param firstName first name of the person
	 * @param lastName last name of the person
	 */
	@Override
	public MedicalRecord getMedicalRecordByName(String firstName, String lastName)
	{
		ArrayList<MedicalRecord> medicalRecords = getMedicalRecords();
		for (MedicalRecord medicalRecord : medicalRecords)
		{
			if (firstName.equals(medicalRecord.getFirstName()) && lastName.equals(medicalRecord.getLastName()))
			{
				Logger.info("Getting " + firstName + " " + lastName + " medical record");
				return medicalRecord;
			}
		}
		Logger.info("Medical record not found");
		return null;
	}

	/**
	 * Add the medical record of a person to the list and writes it
	 * Check if the medical record already exist;
	 *
	 * @param medicalRecord the medicalRecord to add
	 */
	@Override
	public void createMedicalRecord(MedicalRecord medicalRecord)
	{
		if (medicalRecord == null)
		{
			Logger.info("No medical record provided");
			return;
		}

		JsonData data = JsonReader.getInstance().readData();
		ArrayList<MedicalRecord> medicalRecords = getMedicalRecords();
		for (MedicalRecord medicalRecord1 : medicalRecords)
		{
			if (medicalRecord1.getFirstName().equals(medicalRecord.getFirstName()) && medicalRecord1.getLastName().equals(medicalRecord.getLastName()))
			{
				Logger.info("Unable to create medical record : already exist");
				return;
			}
		}

		Logger.info("Adding " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " medical record");
		medicalRecords.add(medicalRecord);
		data.setMedicalRecords(medicalRecords);
		JsonReader.getInstance().setData(data);
		JsonReader.getInstance().writeData();
	}

	/**
	 * Modify the medical record of a person and rewrite the file
	 *
	 * @param medicalRecord the medical record to modify
	 */
	@Override
	public void modifyMedicalRecord(MedicalRecord medicalRecord)
	{
		if (medicalRecord == null)
		{
			Logger.info("No medical record provided");
			return;
		}

		JsonData data = JsonReader.getInstance().readData();
		ArrayList<MedicalRecord> medicalRecords = getMedicalRecords();
		MedicalRecord oldMedicalRecord = null;
		for (MedicalRecord medicalRecord1 : medicalRecords)
		{
			if (medicalRecord1.getFirstName().equals(medicalRecord.getFirstName()) && medicalRecord1.getLastName().equals(medicalRecord.getLastName()))
			{
				oldMedicalRecord = medicalRecord1;
			}
		}

		if (oldMedicalRecord == null)
		{
			Logger.info("Unable to modify medical record : does not exist");
		}
		else
		{
			Logger.info("Modifying " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " medical record");
			medicalRecords.remove(oldMedicalRecord);
			medicalRecords.add(medicalRecord);
			data.setMedicalRecords(medicalRecords);
			JsonReader.getInstance().setData(data);
			JsonReader.getInstance().writeData();
		}
	}

	/**
	 * Delete the medical record of a person and rewrite the file
	 *
	 * @param medicalRecord the medical record to delete
	 */
	@Override
	public void deleteMedicalRecord(MedicalRecord medicalRecord)
	{
		if (medicalRecord == null)
		{
			Logger.info("No medical record provided");
			return;
		}

		JsonData data = JsonReader.getInstance().readData();
		ArrayList<MedicalRecord> medicalRecords = getMedicalRecords();
		boolean alreadyExist = false;
		for (MedicalRecord medicalRecord1 : medicalRecords)
		{
			if (medicalRecord1.getFirstName().equals(medicalRecord.getFirstName()) && medicalRecord1.getLastName().equals(medicalRecord.getLastName()))
			{
				alreadyExist = true;
				break;
			}
		}
		if (!alreadyExist)
		{
			Logger.error("Unable to delete medical record : does not exist");
		}
		else
		{
			Logger.info("Deleting " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " medical record");
			medicalRecords.remove(medicalRecord);
			data.setMedicalRecords(medicalRecords);
			JsonReader.getInstance().setData(data);
			JsonReader.getInstance().writeData();
		}
	}
}
