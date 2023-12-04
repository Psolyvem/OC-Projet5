package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Firestation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class FirestationService implements IFirestationService
{
	private static final Logger logger = LogManager.getLogger();

	@Override
	public ArrayList<Firestation> getFirestations()
	{
		return JsonReader.getInstance().getFirestations();
	}

	@Override
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
		logger.info("Firestation not found");
		return null;
	}

	@Override
	public void createFirestation(Firestation firestation)
	{
		if (getFirestationByAddress(firestation.getAddress()) != null)
		{
			logger.info("Unable to create firestation : already exist");
		}
		else
		{
			JsonReader.getInstance().addFirestation(firestation);
		}
	}

	@Override
	public void modifyFirestation(Firestation firestation)
	{
		if (getFirestationByAddress(firestation.getAddress()) == null)
		{
			logger.info("Unable to modify firestation : does not exist");
		}
		else
		{
			JsonReader.getInstance().deleteFirestation(getFirestationByAddress(firestation.getAddress()));
			JsonReader.getInstance().addFirestation(firestation);
		}
	}

	@Override
	public void deleteFirestation(Firestation firestation)
	{
		JsonReader.getInstance().deleteFirestation(firestation);
	}
}
