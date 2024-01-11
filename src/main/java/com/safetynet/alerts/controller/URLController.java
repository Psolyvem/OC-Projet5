package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.bean.Firestation;
import com.safetynet.alerts.model.bean.Person;
import com.safetynet.alerts.model.service.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@SuppressWarnings("unchecked")
public class URLController
{
	@Autowired
	IURLService urlService;

	//TODO
	@GetMapping(path = "/firestation", params = "stationNumber")
	public JSONObject getFirestationCoverage(@RequestParam(name = "stationNumber", defaultValue = "0") Integer stationNumber)
	{
		return urlService.getFirestationCoverage(stationNumber);
	}

	//TODO
	@GetMapping(path = "/childAlert", params = "address")
	public JSONObject getChildAlert(@RequestParam(name = "address", defaultValue = "") String address)
	{
		return urlService.getChildAlert(address);
	}

	//TODO
	@GetMapping(path = "/phoneAlert", params = "firestation")
	public JSONObject getPhoneAlert(@RequestParam(name = "firestation", defaultValue = "") Integer firestationNumber)
	{
		return urlService.getPhoneAlert(firestationNumber);
	}

	//TODO
	@GetMapping(path = "/fire", params = "address")
	public JSONObject getFireAddress(@RequestParam(name="address", defaultValue = "") String address)
	{
		return urlService.getFireAddress(address);
	}

	//TODO
	@GetMapping(path = "/flood/stations", params = "stations")
	public JSONObject getFloodAlert(@RequestParam(name = "stations", defaultValue = "") List<String> stations)
	{
		return urlService.getFloodAlert(stations);
	}

	//TODO
	@GetMapping(path = "/personInfo", params = {"firstName", "lastName"})
	public JSONObject getPersonInfo(@RequestParam(name = "firstName", defaultValue = "") String firstName, @RequestParam(name = "lastName", defaultValue = "") String lastName)
	{
		return urlService.getPersonInfo(firstName, lastName);
	}

	//TODO
	@GetMapping(path = "/communityEmail", params = "city")
	public JSONObject getCommunityEmail(@RequestParam(name = "city", defaultValue = "") String city)
	{
		return urlService.getCommunityEmail(city);
	}
}
