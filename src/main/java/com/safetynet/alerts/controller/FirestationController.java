package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.bean.Firestation;
import com.safetynet.alerts.model.service.FirestationService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/firestation")
public class FirestationController
{
	FirestationService firestationService = new FirestationService();
	@RequestMapping(method = RequestMethod.GET)
	public ArrayList<Firestation> getFirestations()
	{
		return firestationService.getFirestations();
	}

	@RequestMapping(method = RequestMethod.POST)
	public void postFirestation(@RequestBody Firestation firestation)
	{
		firestationService.createFirestation(firestation);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public void putFirestation(@RequestBody Firestation firestation)
	{
		firestationService.modifyFirestation(firestation);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteFirestation(@RequestBody Firestation firestation)
	{
		firestationService.deleteFirestation(firestation);
	}
}
