package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Firestation;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;

@Service
public class FirestationService
{
	public ArrayList<Firestation> getFirestations()
	{
		return JsonReader.getInstance().getFirestations();
	}

	public Firestation getFirestationByAddress(String address)
	{
		ArrayList<Firestation> firestations = getFirestations();
		for (Firestation firestation : firestations)
		{
			if (address.equals(firestation.getAddress()))
			{
				return firestation;
			}
		}
		Logger.info("Firestation not found");
		return null;
	}

	public void createFirestation(Firestation firestation)
	{
		if (getFirestationByAddress(firestation.getAddress()) != null)
		{
			Logger.info("Unable to create firestation : already exist");
		}
		else
		{
			JsonReader.getInstance().addFirestation(firestation);
		}
	}

	public void modifyFirestation(Firestation firestation)
	{
		if (getFirestationByAddress(firestation.getAddress()) == null)
		{
			Logger.info("Unable to modify firestation : does not exist");
		}
		else
		{
			JsonReader.getInstance().deleteFirestation(getFirestationByAddress(firestation.getAddress()));
			JsonReader.getInstance().addFirestation(firestation);
		}
	}

	public void deleteFirestation(Firestation firestation)
	{
		JsonReader.getInstance().deleteFirestation(firestation);
	}
}
