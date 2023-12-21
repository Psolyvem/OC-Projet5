package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.MedicalRecord;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;

@Service
public class MedicalRecordService
{
	public ArrayList<MedicalRecord> getMedicalRecords()
	{
		return JsonReader.getInstance().getMedicalRecords();
	}

	public MedicalRecord getMedicalRecordByName(String firstName, String lastName)
	{
		ArrayList<MedicalRecord> medicalRecords = getMedicalRecords();
		for (MedicalRecord medicalRecord : medicalRecords)
		{
			if (firstName.equals(medicalRecord.getFirstName()) && lastName.equals(medicalRecord.getLastName()))
			{
				return medicalRecord;
			}
		}
		Logger.info("Medical record not found");
		return null;
	}

	public void createMedicalRecord(MedicalRecord medicalRecord)
	{
		if (getMedicalRecordByName(medicalRecord.getFirstName(), medicalRecord.getLastName()) != null)
		{
			Logger.info("Unable to create medical record : already exist");
		}
		else
		{
			JsonReader.getInstance().addMedicalRecords(medicalRecord);
		}
	}

	public void modifyMedicalRecord(MedicalRecord medicalRecord)
	{
		if (getMedicalRecordByName(medicalRecord.getFirstName(), medicalRecord.getLastName()) == null)
		{
			Logger.info("Unable to modify medical record : does not exist");
		}
		else
		{
			JsonReader.getInstance().deleteMedicalRecords(getMedicalRecordByName(medicalRecord.getFirstName(), medicalRecord.getLastName()));
			JsonReader.getInstance().addMedicalRecords(medicalRecord);
		}
	}

	public void deleteMedicalRecord(MedicalRecord medicalRecord)
	{
		JsonReader.getInstance().deleteMedicalRecords(medicalRecord);
	}
}
