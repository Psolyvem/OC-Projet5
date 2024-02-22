package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Firestation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FirestationServiceTest
{
	FirestationService firestationService;
	Firestation firestation;

	@BeforeEach
	public void setUp()
	{
		firestationService = new FirestationService();

		firestation = new Firestation();
		firestation.setAddress("35 rue test");
		firestation.setStation("9999");
		firestationService.createFirestation(firestation);
	}

	@AfterEach
	public void breakDown()
	{
		firestationService.deleteFirestation(firestationService.getFirestationByAddress("35 rue test"));
	}

	@Test
	public void getFirestationByAddressTest()
	{
		assertEquals(firestation, firestationService.getFirestationByAddress("35 rue test"));
	}

	@Test
	public void modifyFirestationTest()
	{
		firestation.setStation("9998");
		firestationService.modifyFirestation(firestation);

		assertEquals("9998", firestationService.getFirestationByAddress("35 rue test").getStation());
	}

	@Test
	public void deletePersonTest()
	{
		firestationService.deleteFirestation(firestation);

		assertEquals(null, firestationService.getFirestationByAddress("35 rue test"));
	}
}
