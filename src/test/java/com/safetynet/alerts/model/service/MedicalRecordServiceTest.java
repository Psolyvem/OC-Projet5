package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.MedicalRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class MedicalRecordServiceTest
{
	MedicalRecordService medicalRecordService;
	MedicalRecord medicalRecord;

	@BeforeEach
	public void setUp()
	{
		medicalRecordService = new MedicalRecordService();

		medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName("Jean");
		medicalRecord.setLastName("Bon");
		medicalRecord.setBirthDate("20/01/1982");
		medicalRecord.setAllergies(new ArrayList<>(){{add("fleur"); add("judo");}});
		medicalRecord.setMedications(new ArrayList<>(){{add("doliprane:1000mg"); add("gaspacho:100mg");}});
		medicalRecordService.createMedicalRecord(medicalRecord);
	}

	@AfterEach
	public void breakDown()
	{
		medicalRecordService.deleteMedicalRecord(medicalRecordService.getMedicalRecordByName(medicalRecord.getFirstName(), medicalRecord.getLastName()));
	}

	@Test
	public void createMedicalRecordTest()
	{
		assertEquals(medicalRecord, medicalRecordService.getMedicalRecordByName("Jean", "Bon"));
	}

	@Test
	public void modifyMedicalRecordTest()
	{
		medicalRecord.setBirthDate("31/12/1980");
		medicalRecordService.modifyMedicalRecord(medicalRecord);

		assertEquals("31/12/1980", medicalRecordService.getMedicalRecordByName("Jean", "Bon").getBirthDate());
	}

	@Test
	public void deleteMedicalRecordTest()
	{
		medicalRecordService.deleteMedicalRecord(medicalRecord);

		assertNull(medicalRecordService.getMedicalRecordByName("Jean", "Bon"));
	}
}
