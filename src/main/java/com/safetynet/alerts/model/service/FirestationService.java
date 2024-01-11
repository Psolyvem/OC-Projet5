package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.Firestation;
import com.safetynet.alerts.model.bean.JsonData;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;

@Service
public class FirestationService implements IFirestationService
{
	/**
	 * Get all the fire stations from the file
	 *
	 */
	@Override
	public ArrayList<Firestation> getFirestations()
	{
		return JsonReader.getInstance().getData().getFirestations();
	}

	/**
	 * Get a fire station from the file
	 *
	 * @param address the address of the fire station
	 */
	@Override
	public Firestation getFirestationByAddress(String address)
	{
		if (address == null)
		{
			Logger.info("No fire station provided");
			return null;
		}

		ArrayList<Firestation> firestations = getFirestations();
		for (Firestation firestation : firestations)
		{
			if (address.equals(firestation.getAddress()))
			{
				Logger.info("Getting fire station for address : " + firestation.getAddress());
				return firestation;
			}
		}
		Logger.info("Firestation not found");
		return null;
	}

	/**
	 * Add a fire station to the list and writes it
	 * Check if the fire station already exist;
	 *
	 * @param firestation the fire station to add
	 */
	@Override
	public void createFirestation(Firestation firestation)
	{
		if (firestation == null)
		{
			Logger.info("No fire station provided");
			return;
		}

		JsonData data = JsonReader.getInstance().getData();
		ArrayList<Firestation> firestations = data.getFirestations();
		for (Firestation firestation1 : firestations)
		{
			if (firestation1.getAddress().equals(firestation.getAddress()))
			{
				Logger.info("Unable to create fire station : already exist");
				return;
			}
		}

		Logger.info("Adding firestation n° " + firestation.getStation() + "  to address : " + firestation.getAddress());
		firestations.add(firestation);
		data.setFirestations(firestations);
		JsonReader.getInstance().setData(data);
		JsonReader.getInstance().writeData();
	}

	/**
	 * Modify a fire station and rewrite the file
	 *
	 * @param firestation the fire station to modify
	 */
	@Override
	public void modifyFirestation(Firestation firestation)
	{
		if (firestation == null)
		{
			Logger.info("No fire station provided");
			return;
		}
		JsonData data = JsonReader.getInstance().getData();
		ArrayList<Firestation> firestations = data.getFirestations();
		Firestation oldFirestation = null;
		for (Firestation firestation1 : firestations)
		{
			if (firestation1.getAddress().equals(firestation.getAddress()))
			{
				oldFirestation = firestation1;
			}
		}

		if (oldFirestation == null)
		{
			Logger.info("Unable to modify fire station : does not exist");
		}
		else
		{
			Logger.info("Modifying fire station n° " +firestation.getStation() + " for address : " + firestation.getAddress());
			firestations.remove(oldFirestation);
			firestations.add(firestation);
			data.setFirestations(firestations);
			JsonReader.getInstance().setData(data);
			JsonReader.getInstance().writeData();
		}
	}

	/**
	 * Delete a fire station and rewrite the file
	 *
	 * @param firestation the fire station to delete
	 */
	@Override
	public void deleteFirestation(Firestation firestation)
	{
		if (firestation == null)
		{
			Logger.info("No fire station provided");
			return;
		}

		JsonData data = JsonReader.getInstance().getData();
		ArrayList<Firestation> firestations = data.getFirestations();
		boolean alreadyExists = false;
		for (Firestation firestation1 : firestations)
		{
			if (firestation1.getAddress().equals(firestation.getAddress()))
			{
				alreadyExists = true;
				break;
			}
		}
		if (!alreadyExists)
		{
			Logger.error("Unable to delete person : does not exist");
		}
		else
		{
			Logger.info("Deleting fire station n° " +firestation.getStation() + " for address : " + firestation.getAddress());
			firestations.remove(firestation);
			data.setFirestations(firestations);
			JsonReader.getInstance().setData(data);
			JsonReader.getInstance().writeData();
		}
	}
}
