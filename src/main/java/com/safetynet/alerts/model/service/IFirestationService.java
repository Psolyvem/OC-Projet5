package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Firestation;

import java.util.ArrayList;

public interface IFirestationService
{
	public ArrayList<Firestation> getFirestations();
	public Firestation getFirestationByAddress(String address);
	public void createFirestation(Firestation firestation);
	public void modifyFirestation(Firestation firestation);
	public void deleteFirestation(Firestation firestation);
}
