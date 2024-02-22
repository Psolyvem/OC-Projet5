package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Firestation;
import com.safetynet.alerts.model.bean.MedicalRecord;
import com.safetynet.alerts.model.bean.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tinylog.Logger;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UrlServiceTest
{
	@Autowired
	URLService urlService;
	@Autowired
	PersonService personService;
	@Autowired
	MedicalRecordService medicalRecordService;
	@Autowired
	FirestationService firestationService;
	Person person, person2, person3;
	MedicalRecord medicalRecord, medicalRecord2, medicalRecord3;
	Firestation firestation, firestation2;

	@BeforeEach
	public void setUpEach()
	{
		try
		{
			person = new Person();
			person.setFirstName("Jean");
			person.setLastName("Test");
			person.setAddress("35 rue test");
			person.setCity("Ecouflant");
			person.setZip("49000");
			person.setEmail("jean.test@test.freu");
			person.setPhone("0123456789");
			personService.createPerson(person);

			person2 = new Person();
			person2.setFirstName("Pierre");
			person2.setLastName("Test");
			person2.setAddress("35 rue test");
			person2.setCity("Ecouflant");
			person2.setZip("49000");
			person2.setEmail("pierre.test@test.freu");
			person2.setPhone("0123456789");
			personService.createPerson(person2);

			person3 = new Person();
			person3.setFirstName("Jacques");
			person3.setLastName("Test");
			person3.setAddress("42 rue test");
			person3.setCity("Ecouflant");
			person3.setZip("49000");
			person3.setEmail("jacques.test@test.freu");
			person3.setPhone("9876543210");
			personService.createPerson(person3);
		}
		catch(Exception e)
		{
			Logger.error("Error setting up tests while creating person");
		}
		try
		{
			medicalRecord = new MedicalRecord();
			medicalRecord.setFirstName("Jean");
			medicalRecord.setLastName("Test");
			medicalRecord.setBirthDate("01/20/1982");
			medicalRecord.setAllergies(new ArrayList<String>(){{add("fleur"); add("judo");}});
			medicalRecord.setMedications(new ArrayList<String>(){{add("doliprane:1000mg"); add("gaspacho:100mg");}});
			medicalRecordService.createMedicalRecord(medicalRecord);

			medicalRecord2 = new MedicalRecord();
			medicalRecord2.setFirstName("Pierre");
			medicalRecord2.setLastName("Test");
			medicalRecord2.setBirthDate("01/20/2010");
			medicalRecord2.setAllergies(new ArrayList<String>(){{add("tapis");}});
			medicalRecord2.setMedications(new ArrayList<String>(){{add("yaourt:800mg");}});
			medicalRecordService.createMedicalRecord(medicalRecord2);

			medicalRecord3 = new MedicalRecord();
			medicalRecord3.setFirstName("Jacques");
			medicalRecord3.setLastName("Test");
			medicalRecord3.setBirthDate("01/20/1995");
			medicalRecord3.setAllergies(new ArrayList<String>(){{add("sol");}});
			medicalRecord3.setMedications(new ArrayList<String>(){{add("granola:100mg");}});
			medicalRecordService.createMedicalRecord(medicalRecord3);
		}
		catch(Exception e)
		{
			Logger.error("Error setting up tests while creating medical record");
		}
		try
		{
			firestation = new Firestation();
			firestation.setAddress("35 rue test");
			firestation.setStation("9999");
			firestationService.createFirestation(firestation);

			firestation2 = new Firestation();
			firestation2.setAddress("42 rue test");
			firestation2.setStation("9998");
			firestationService.createFirestation(firestation2);
		}
		catch(Exception e)
		{
			Logger.error("Error setting up tests while creating fire station");
		}
	}

	@AfterEach
	public void breakDownEach()
	{
		personService.deletePerson(person);
		personService.deletePerson(person2);
		medicalRecordService.deleteMedicalRecord(medicalRecord);
		medicalRecordService.deleteMedicalRecord(medicalRecord2);
		firestationService.deleteFirestation(firestation);
	}

	@Test
	public void getFirestationCoverageTest()
	{
		JSONObject response = urlService.getFirestationCoverage(1);

		assertThat(response.get("persons")).isNotNull();
	}

	@Test
	public void getChildAlertTest()
	{
		JSONObject response = urlService.getChildAlert("35 rue test");
		JSONArray children = (JSONArray) response.get("children");
		JSONObject testedChild = (JSONObject) children.get(0);
		JSONArray adults = (JSONArray) response.get("adults");
		JSONObject testedAdult = (JSONObject) adults.get(0);

		JSONObject expectedChild = new JSONObject();
		expectedChild.put("firstName", person2.getFirstName());
		expectedChild.put("lastName", person2.getLastName());
		expectedChild.put("age", "14");

		JSONObject expectedAdult = new JSONObject();
		expectedAdult.put("firstName", person.getFirstName());
		expectedAdult.put("lastName", person.getLastName());
		expectedAdult.put("age", "42");

		assertThat(testedChild.get("firstName")).isEqualTo("Pierre");
		assertThat(testedChild.get("lastName")).isEqualTo("Test");
		assertThat(testedChild.get("age")).isEqualTo("14");

		assertThat(testedAdult.get("firstName")).isEqualTo("Jean");
		assertThat(testedAdult.get("lastName")).isEqualTo("Test");
		assertThat(testedAdult.get("age")).isEqualTo("42");
	}

	@Test
	public void getPhoneAlertTest()
	{
		assertThat(urlService.getPhoneAlert(9999).get("phonenumbers")).toString().contains("0123456789");
	}

	@Test
	public void getFireAddressTest()
	{
		JSONObject response = urlService.getFireAddress("35 rue test");
		JSONArray persons = (JSONArray) response.get("persons");
		JSONObject person = (JSONObject) persons.get(0);
		assertThat(response.get("firestationNumber")).isEqualTo("9999");
		assertThat(person.get("firstName")).isEqualTo(this.person.getFirstName());
		assertThat(person.get("lastName")).isEqualTo(this.person.getLastName());
		assertThat(person.get("medications")).isNotNull();
	}

	@Test
	public void getFloodAlertTest()
	{
		ArrayList<String> listStations = new ArrayList<>();
		listStations.add("9998");
		listStations.add("9999");
		JSONObject response = urlService.getFloodAlert(listStations);
		JSONObject station = (JSONObject) response.get("9998");
		JSONObject station2 = (JSONObject) response.get("9999");

		assertThat(station.get("42 rue test")).isNotNull();
		assertThat(station2.get("35 rue test")).isNotNull();
	}

	@Test
	public void getPersonInfoTest()
	{
		JSONObject response = urlService.getPersonInfo("Jean", "Test");
		JSONArray persons = (JSONArray) response.get("persons");
		JSONObject person = (JSONObject) persons.get(0);

		assertThat(person.get("firstName")).isEqualTo(this.person.getFirstName());
		assertThat(person.get("lastName")).isEqualTo(this.person.getLastName());
		assertThat(person.get("medications")).isNotNull();
	}

	@Test
	public void getCommunityEmailTest()
	{
		JSONObject response = urlService.getCommunityEmail("Ecouflant");
		JSONArray emails = (JSONArray) response.get("emails");

		assertThat(emails.toString().contains("jean.test@test.freu")).isTrue();
		assertThat(emails.toString().contains("pierre.test@test.freu")).isTrue();
		assertThat(emails.toString().contains("jacques.test@test.freu")).isTrue();
	}

}
