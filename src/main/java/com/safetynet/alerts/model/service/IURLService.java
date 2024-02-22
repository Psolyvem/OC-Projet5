package com.safetynet.alerts.model.service;

import org.json.simple.JSONObject;

import java.util.List;

public interface IURLService
{
	JSONObject getFirestationCoverage(Integer stationNumber);
	JSONObject getChildAlert(String address);
	JSONObject getPhoneAlert(Integer firestationNumber);
	JSONObject getFireAddress(String address);
	JSONObject getFloodAlert(List<String> stations);
	JSONObject getPersonInfo(String firstName, String lastName);
	JSONObject getCommunityEmail(String city);
}
