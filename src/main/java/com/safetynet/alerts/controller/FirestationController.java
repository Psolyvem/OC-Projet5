package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.bean.Firestation;
import com.safetynet.alerts.model.service.JsonReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class FirestationController
{
	@GetMapping("/firestation")
	public ArrayList<Firestation> getFirestation()
	{
		return JsonReader.readFirestations("src/main/resources/data.json");
	}
}
