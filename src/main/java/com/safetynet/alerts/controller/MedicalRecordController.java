package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.bean.MedicalRecord;
import com.safetynet.alerts.model.service.MedicalRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/medicalrecord")
public class MedicalRecordController
{
	MedicalRecordService medicalRecordService = new MedicalRecordService();

	@RequestMapping(method = RequestMethod.GET)
	public ArrayList<MedicalRecord> getMedicalRecords(/*@RequestParam(name = "id", defaultValue = "") int id*/)
	{
		return medicalRecordService.getMedicalRecords();
	}

	@RequestMapping(method = RequestMethod.POST)
	public void postMedicalRecord(@RequestBody MedicalRecord medicalRecord)
	{
		medicalRecordService.createMedicalRecord(medicalRecord);
	}
	@RequestMapping(method = RequestMethod.PUT)
	public void putMedicalRecord(@RequestBody MedicalRecord medicalRecord)
	{
		medicalRecordService.modifyMedicalRecord(medicalRecord);
	}
	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord)
	{
		medicalRecordService.deleteMedicalRecord(medicalRecord);
	}
}
