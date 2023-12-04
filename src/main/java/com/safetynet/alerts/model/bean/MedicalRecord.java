package com.safetynet.alerts.model.bean;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MedicalRecord
{
	private String firstName;
	private String lastName;
	private String birthDate;
	private ArrayList<String> medications;
	private ArrayList<String> allergies;
}
