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
		firestation.setAddress("35 rue Gudu");
		firestation.setStation("3");
		firestationService.createFirestation(firestation);
	}

	@AfterEach
	public void breakDown()
	{
		firestationService.deleteFirestation(firestationService.getFirestationByAddress("35 rue Gudu"));
	}

	@Test
	public void getFirestationByAddressTest()
	{
		assertEquals(firestation, firestationService.getFirestationByAddress("35 rue Gudu"));
	}

	@Test
	public void modifyFirestationTest()
	{
		firestation.setStation("4");
		firestationService.modifyFirestation(firestation);

		assertEquals("4", firestationService.getFirestationByAddress("35 rue Gudu").getStation());
	}

	@Test
	public void deletePersonTest()
	{
		firestationService.deleteFirestation(firestation);

		assertEquals(null, firestationService.getFirestationByAddress("35 rue Gudu"));
	}
}
