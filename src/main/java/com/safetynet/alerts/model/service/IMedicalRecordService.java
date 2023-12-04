package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.MedicalRecord;

import java.util.ArrayList;

public interface IMedicalRecordService
{
	ArrayList<MedicalRecord> getMedicalRecords();
	MedicalRecord getMedicalRecordByName(String firstName, String lastName);
	void createMedicalRecord(MedicalRecord medicalRecord);
	void modifyMedicalRecord(MedicalRecord medicalRecord);
	void deleteMedicalRecord(MedicalRecord medicalRecord);
}
