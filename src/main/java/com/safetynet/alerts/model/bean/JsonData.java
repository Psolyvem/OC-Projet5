package com.safetynet.alerts.model.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 *  Java representation of the Json data file
 */

@Data
@Component
public class JsonData
{
	ArrayList<Person> persons;
	ArrayList<Firestation> firestations;
	ArrayList<MedicalRecord> medicalRecords;
}
