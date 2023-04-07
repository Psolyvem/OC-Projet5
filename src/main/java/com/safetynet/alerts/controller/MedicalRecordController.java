package com.safetynet.alerts.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordController
{
//	@GetMapping("/medicalrecord")
//	public MedicalRecord getMedicalRecord(@RequestParam(name = "id", defaultValue = "") int id)
//	{
//		return JsonReader.readMedicalRecords("src/main/resources/data.json").get(id);
//	}

	@PostMapping("/medicalrecord")
	public void postMedicalRecord()
	{

	}
	@PutMapping("/medicalrecord")
	public void putMedicalRecord()
	{

	}
	@DeleteMapping("/medicalrecord")
	public void deleteMedicalRecord()
	{

	}
}
