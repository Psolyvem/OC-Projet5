package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.MedicalRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MedicalRecordService implements IMedicalRecordService
{
	private static final Logger logger = LogManager.getLogger();

	@Override
	public ArrayList<MedicalRecord> getMedicalRecords()
	{
		return JsonReader.getInstance().getMedicalRecords();
	}

	@Override
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
		logger.info("Medical record not found");
		return null;
	}

	@Override
	public void createMedicalRecord(MedicalRecord medicalRecord)
	{
		if (getMedicalRecordByName(medicalRecord.getFirstName(), medicalRecord.getLastName()) != null)
		{
			logger.error("Unable to create medical record : already exist");
		}
		else
		{
			JsonReader.getInstance().addMedicalRecords(medicalRecord);
		}
	}

	@Override
	public void modifyMedicalRecord(MedicalRecord medicalRecord)
	{
		if (getMedicalRecordByName(medicalRecord.getFirstName(), medicalRecord.getLastName()) == null)
		{
			logger.info("Unable to modify medical record : does not exist");
		}
		else
		{
			JsonReader.getInstance().deleteMedicalRecords(getMedicalRecordByName(medicalRecord.getFirstName(), medicalRecord.getLastName()));
			JsonReader.getInstance().addMedicalRecords(medicalRecord);
		}
	}

	@Override
	public void deleteMedicalRecord(MedicalRecord medicalRecord)
	{
		JsonReader.getInstance().deleteMedicalRecords(medicalRecord);
	}
}
