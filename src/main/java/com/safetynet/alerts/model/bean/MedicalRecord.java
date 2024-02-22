package com.safetynet.alerts.model.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Data
public class MedicalRecord
{
	private String firstName;
	private String lastName;
	private String birthDate;
	private ArrayList<String> medications;
	private ArrayList<String> allergies;
}
