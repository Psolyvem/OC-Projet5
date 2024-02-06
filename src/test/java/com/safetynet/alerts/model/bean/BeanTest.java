package com.safetynet.alerts.model.bean;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BeanTest
{
	@Test
	public void personTest()
	{
		Person person = new Person();
		Person person1 = new Person();

		assertThat(person.hashCode()).isNotNull();
		assertThat(person.toString()).isEqualTo("Person(firstName=null, lastName=null, address=null, city=null, zip=null, phone=null, email=null)");
		assertThat(person.equals(person1)).isTrue();
	}

	@Test
	public void medicalRecordTest()
	{
		MedicalRecord medicalRecord = new MedicalRecord();
		MedicalRecord medicalRecord1 = new MedicalRecord();

		assertThat(medicalRecord.hashCode()).isNotNull();
		assertThat(medicalRecord.toString()).isEqualTo("MedicalRecord(firstName=null, lastName=null, birthDate=null, medications=null, allergies=null)");
		assertThat(medicalRecord.equals(medicalRecord1)).isTrue();
	}

	@Test
	public void firestationTest()
	{
		Firestation firestation = new Firestation();
		Firestation firestation1 = new Firestation();

		assertThat(firestation.hashCode()).isNotNull();
		assertThat(firestation.toString()).isEqualTo("Firestation(address=null, station=null)");
		assertThat(firestation.equals(firestation1)).isTrue();
	}

	@Test
	public void jsonDataTest()
	{
		JsonData jsonData = new JsonData();
		JsonData jsonData1 = new JsonData();

		assertThat(jsonData.hashCode()).isNotNull();
		assertThat(jsonData.toString()).isEqualTo("JsonData(persons=null, firestations=null, medicalRecords=null)");
		assertThat(jsonData.equals(jsonData1)).isTrue();
	}
}
