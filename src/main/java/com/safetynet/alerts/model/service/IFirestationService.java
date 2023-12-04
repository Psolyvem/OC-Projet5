package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Firestation;

import java.util.ArrayList;

public interface IFirestationService
{
	ArrayList<Firestation> getFirestations();
	Firestation getFirestationByAddress(String address);
	void createFirestation(Firestation firestation);
	void modifyFirestation(Firestation firestation);
	void deleteFirestation(Firestation firestation);
}
