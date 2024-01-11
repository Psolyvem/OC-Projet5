package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.bean.Firestation;
import com.safetynet.alerts.model.bean.Person;
import com.safetynet.alerts.model.service.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@RestController
public class FirestationController
{
	@Autowired
	IFirestationService firestationService;

	@GetMapping(path = "/firestation")
	public ArrayList<Firestation> getFirestations()
	{
		return firestationService.getFirestations();
	}

	@GetMapping(path = "/firestation", params = "address")
	public Firestation getFirestationByAddress(@RequestParam(name = "address") String address)
	{
			return firestationService.getFirestationByAddress(address);
	}


	@PostMapping("/firestation")
	public void postFirestation(@RequestBody Firestation firestation)
	{
		firestationService.createFirestation(firestation);
	}

	@PutMapping("/firestation")
	public void putFirestation(@RequestBody Firestation firestation)
	{
		firestationService.modifyFirestation(firestation);
	}

	@DeleteMapping("/firestation")
	public void deleteFirestation(@RequestBody Firestation firestation)
	{
		firestationService.deleteFirestation(firestation);
	}
}
