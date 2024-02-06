package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.MedicalRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class MedicalRecordServiceTest
{
	@Autowired
	MedicalRecordService medicalRecordService;
	@Autowired
	MedicalRecord medicalRecord;

	@BeforeEach
	public void setUp()
	{
		medicalRecord.setFirstName("Jean");
		medicalRecord.setLastName("Test");
		medicalRecord.setBirthDate("01/20/1982");
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
		assertEquals(medicalRecord, medicalRecordService.getMedicalRecordByName("Jean", "Test"));
	}

	@Test
	public void modifyMedicalRecordTest()
	{
		medicalRecord.setBirthDate("12/31/1980");
		medicalRecordService.modifyMedicalRecord(medicalRecord);

		assertEquals("12/31/1980", medicalRecordService.getMedicalRecordByName("Jean", "Test").getBirthDate());
	}

	@Test
	public void deleteMedicalRecordTest()
	{
		medicalRecordService.deleteMedicalRecord(medicalRecord);

		assertNull(medicalRecordService.getMedicalRecordByName("Jean", "Test"));
	}
}
