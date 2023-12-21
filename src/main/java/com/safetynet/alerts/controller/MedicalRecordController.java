package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.bean.MedicalRecord;
import com.safetynet.alerts.model.service.MedicalRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController

public class MedicalRecordController
{
	MedicalRecordService medicalRecordService = new MedicalRecordService();

	@GetMapping("/medicalrecord")
	public ArrayList<MedicalRecord> getMedicalRecords()
	{
		return medicalRecordService.getMedicalRecords();
	}

	@PostMapping("/medicalrecord")
	public void postMedicalRecord(@RequestBody MedicalRecord medicalRecord)
	{
		medicalRecordService.createMedicalRecord(medicalRecord);
	}
	@PutMapping("/medicalrecord")
	public void putMedicalRecord(@RequestBody MedicalRecord medicalRecord)
	{
		medicalRecordService.modifyMedicalRecord(medicalRecord);
	}
	@DeleteMapping("/medicalrecord")
	public void deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord)
	{
		medicalRecordService.deleteMedicalRecord(medicalRecord);
	}
}
