package com.safetynet.alerts;

import com.safetynet.alerts.model.JsonReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlertsApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(AlertsApplication.class, args);

		JsonReader jsonReader = new JsonReader();
		jsonReader.readPersons("src/main/resources/data.json");
		jsonReader.readFirestations("src/main/resources/data.json");
		jsonReader.readMedicalRecords("src/main/resources/data.json");
	}

}
